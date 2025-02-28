#include <mutex>
#include <condition_variable>
#include <thread>
#include <functional>
#include <iostream>

#define N 5

enum PhilosopherState {
    PHILOSOPHER_IDLE,
    PHILOSOPHER_HUNGRY,
    PHILOSOPHER_EATING,
};

enum ForkState {
    FORK_FREE,
    FORK_OCCUPIED,
};

class DiningPhilosophers {
private:
    std::mutex philosopherMutexes[N];
    enum PhilosopherState philosophers[N];
    std::condition_variable philosopherConds[N];

    std::mutex forkMutex;
    enum ForkState forks[N];
    std::condition_variable forkCond;

    std::thread threads[N];
    bool work;
public:
    DiningPhilosophers() {
        for (int i = 0; i < N; i++) {
            philosophers[i] = PHILOSOPHER_IDLE;
            forks[i] = FORK_FREE;
        }

        work = true;
        for (int i = 0; i < N; i++) {
            threads[i] = std::thread(&DiningPhilosophers::loop, this, i);
        }
    }

    ~DiningPhilosophers() {
        work = false;

        for (int i = 0; i < N; i++) {
            philosopherConds[i].notify_all();
            while (!threads[i].joinable());
            threads[i].join();
        }
    }

    void philosopherWantsToEat(int philosopher) {
        std::unique_lock lock(philosopherMutexes[philosopher]);
        philosopherConds[philosopher].wait(lock, [this, philosopher]{ return philosophers[philosopher] == PHILOSOPHER_IDLE; });

        philosophers[philosopher] = PHILOSOPHER_HUNGRY;
        philosopherConds[philosopher].notify_all();
    }

    void wantsToEat(int philosopher,
                    std::function<void()> pickLeftFork,
                    std::function<void()> pickRightFork,
                    std::function<void()> eat,
                    std::function<void()> putLeftFork,
                    std::function<void()> putRightFork) {
        philosopherWantsToEat(philosopher);

        std::unique_lock philLock(philosopherMutexes[philosopher]);
        philosopherConds[philosopher].wait(philLock, [this, philosopher]{ return philosophers[philosopher] == PHILOSOPHER_EATING; });

        if (philosophers[philosopher] == PHILOSOPHER_EATING) {
            int leftFork = philosopher;
            int rightFork = incI(philosopher);
            pickLeftFork();
            pickRightFork();

            eat();

            std::unique_lock forkLock(forkMutex);
            putLeftFork();
            forks[leftFork] = FORK_FREE;

            putRightFork();
            forks[rightFork] = FORK_FREE;
            forkCond.notify_all();
            forkLock.unlock();

            philosophers[philosopher] = PHILOSOPHER_IDLE;
            philosopherConds[philosopher].notify_all();
            philLock.unlock();
        }
    }

    int incI(int index) {
        return (index + 1) % N;
    }

    void loop(int philosopher) {
        while (work) {
            std::unique_lock philLock(philosopherMutexes[philosopher]);
            philosopherConds[philosopher].wait(philLock, [this, philosopher]{ return philosophers[philosopher] == PHILOSOPHER_HUNGRY || !work; });

            if (!work) {
                break;
            }

            if (philosophers[philosopher] == PHILOSOPHER_HUNGRY) {
                int leftFork = philosopher;
                int rightFork = incI(philosopher);

                std::unique_lock forkLock(forkMutex);
                forkCond.wait(forkLock, [this, leftFork, rightFork]{ return forks[leftFork] == FORK_FREE && forks[rightFork] == FORK_FREE; });

                forks[leftFork] = FORK_OCCUPIED;
                forks[rightFork] = FORK_OCCUPIED;
                philosophers[philosopher] = PHILOSOPHER_EATING;
                philosopherConds[philosopher].notify_all();
                forkCond.notify_all();
                forkLock.unlock();
            }
            philLock.unlock();
        }
    }
};

