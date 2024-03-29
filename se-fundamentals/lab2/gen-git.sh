#!/bin/bash

# Most of the function assume that PWD is REPO_DIR.

COMMITS_DIR="./commits"
COMMITS_DIR_ABS_PATH="$(pwd)/$COMMITS_DIR"
REPO_DIR="./lab2-git"
REPO_DIR_ABS_PATH="$(pwd)/$REPO_DIR"

say() {
    echo "$@" >&2;
}

create_dummy_init_commits() {
    touch "tmp"
    git add --all
    git commit -m "add tmp"

    rm "tmp"
    git add --all
    git commit -m "rm tmp"
}

prepare_repo() {
    if [[ -d "$REPO_DIR" ]]
    then
        say "Directory '$REPO_DIR' already exists."
        exit 1
    fi

    mkdir -p "$REPO_DIR"

    cd "$REPO_DIR"

    git init .

    create_dummy_init_commits

    say "Repo created"
}

create_commit() {
    local commit_n=$1
    local archive_path="$COMMITS_DIR_ABS_PATH/commit$commit_n.zip"

    git rm -rf *
    unzip -o -d "." "$archive_path"

    git add --all
    git commit -m "commit message (#$commit_n)"

    local branch="$(git branch --show-current)"
    local username="$(git config user.name)"

    say "Commit #$commit_n on $branch (by $username)"
}

switch_user() {
    local user=$1

    local name=""
    local email=""

    if [[ "$user" == "2" ]]
    then
        name="blue"
        email="blue@blue.com"
    else
        name="red"
        email="red@red.com"
    fi

    git config --local user.name "$name"
    git config --local user.email "$email"

    local username="$(git config user.name)"

    say "Switched to user $username"
}

switch_branch() {
    local branch=$1
    git switch "$branch"

    say "Switched to branch $branch"
}

create_branch() {
    local branch=$1
    git branch "$branch"

    say "Created branch $branch"
}

print_delimiter() {
    sleep 1
    say "---------"
}

merge_branch() {
    local other=$1
    local commit_n=$2

    local current="$(git branch --show-current)"

    git merge "$other" -m "merge message (#$commit_n)"

    local unmerged=$(git ls-files -u)

    if [[ ! -z "$unmerged" ]]
    then
        say "Merge conflict occured. Please resolve all conflicts in other terminal session then hit Enter here."
        say "Conflicts occured in:"
        say "$unmerged"
        read -n 1 -s -r

        git add --all
        git commit --no-edit
    fi

    say "Merged $other into $current"
}

main() {
    prepare_repo

    print_delimiter
    # r0
    switch_user 1
    create_branch "branch1"
    switch_branch "branch1"
    create_commit 0

    print_delimiter
    # r1
    switch_user 2
    create_branch "branch2"
    switch_branch "branch2"
    create_commit 1

    print_delimiter
    # r2
    switch_user 1
    switch_branch "branch1"
    create_commit 2

    print_delimiter
    # r3
    switch_user 2
    create_branch "branch3"
    switch_branch "branch3"
    create_commit 3

    print_delimiter
    # r4
    create_commit 4

    print_delimiter
    # r5
    switch_branch "branch2"
    create_commit 5

    print_delimiter
    # r6
    switch_branch "branch3"
    create_commit 6

    print_delimiter
    # r7
    switch_user 1
    switch_branch "branch1"
    create_commit 7

    print_delimiter
    # r8
    switch_user 2
    switch_branch "branch3"
    create_commit 8

    print_delimiter
    # r9
    switch_branch "branch2"
    create_commit 9

    print_delimiter
    # r10
    create_commit 10

    print_delimiter
    # r11
    switch_user 1
    switch_branch "branch1"
    create_commit 11

    print_delimiter
    # r12
    switch_user 2
    switch_branch "branch2"
    create_commit 12

    # option 2: r13 and r14 are merge commits with unrelated changes (but WHY?)
    print_delimiter
    # r13: merge branch2 into branch3
    switch_branch "branch3"
    merge_branch "branch2" 13

    print_delimiter
    # r14: merge branch3 into branch1
    switch_user 1
    switch_branch "branch1"
    merge_branch "branch3" 14
}

main >/dev/null