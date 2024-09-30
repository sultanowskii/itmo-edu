% Игровые классы.
% class(ClassName).
class(scout).
class(soldier).
class(pyro).
class(demoman).
class(heavy).
class(engineer).
class(medic).
class(sniper).
class(spy).

% Макс. здоровье классов.
class_max_hp(scout, 125).
class_max_hp(soldier, 200).
class_max_hp(pyro, 175).
class_max_hp(demoman, 175).
class_max_hp(heavy, 300).
class_max_hp(engineer, 125).
class_max_hp(medic, 150).
class_max_hp(sniper, 125).
class_max_hp(spy, 125).

% Средний урон классов.
class_avg_dmg(scout, 40).
class_avg_dmg(soldier, 55).
class_avg_dmg(pyro, 50).
class_avg_dmg(demoman, 70).
class_avg_dmg(heavy, 90).
class_avg_dmg(engineer, 40).
class_avg_dmg(medic, 15).
class_avg_dmg(sniper, 50).
class_avg_dmg(spy, 30).

% Команды: RED и BLU
% team(TeamName).
team(red).
team(blu).

% Виды аптечек.
health_pack(small).
health_pack(medium).
health_pack(large).

% Процент здоровья, который восстанавливают виды аптечек.
% health_pack(HealthPackType, HealFactor)
health_pack_factor(small, 0.205).
health_pack_factor(medium, 0.5).
health_pack_factor(large, 1.0).

% Предметы.
% item(ItemName).
item(razorback).

% Игроки.
% player(Name).
player('dima').
player('alex').
player('vasya').
player('anne').
player('robin').
player('bill').
player('pablo').
player('screensaver_123').
player('garry').
player('terry').

% Классы игроков
player_class('dima', scout).
player_class('alex', soldier).
player_class('vasya', pyro).
player_class('anne', heavy).
player_class('robin', sniper).
player_class('bill', soldier).
player_class('pablo', engineer).
player_class('screensaver_123', medic).
player_class('garry', sniper).
player_class('terry', spy).

% Здоровье игроков
player_hp('dima', 120).
player_hp('alex', 174).
player_hp('vasya', 98).
player_hp('anne', 299).
player_hp('robin', 4).
player_hp('bill', 132).
player_hp('pablo', 101).
player_hp('screensaver_123', 102).
player_hp('garry', 54).
player_hp('terry', 3).

% Команды игроков.
player_team('dima', red).
player_team('alex', red).
player_team('vasya', red).
player_team('anne', red).
player_team('robin', red).
player_team('bill', blu).
player_team('pablo', blu).
player_team('screensaver_123', blu).
player_team('garry', blu).
player_team('terry', blu).

% Наличие у игроков предметов
% has_item(PlayerName, ItemName).
has_item('robin', razorback).

% Находятся ли два игрока в разных командах?
is_different_team(PlayerA, PlayerB) :-
    player_team(PlayerA, TeamA),
    player_team(PlayerB, TeamB),
    TeamA \== TeamB.

% Являются ли указанные игроки разными игроками?
is_different_player(PlayerA, PlayerB) :-
    PlayerA \== PlayerB.

% Может ли Player повергнуть TargetPlayer?
can_kill_player(PlayerName, TargetPlayerName) :-
    is_different_player(PlayerName, TargetPlayerName),
    is_different_team(PlayerName, TargetPlayerName),
    player_class(PlayerName, PlayerClass),
    class_avg_dmg(PlayerClass, AvgDamage),
    player_hp(TargetPlayerName, HP),
    HP =< AvgDamage.

% Может ли Player повергнуть TargetPlayer выстрелом в голову (доступно только снайперу)?
can_headshot_player(PlayerName, TargetPlayerName) :-
    is_different_player(PlayerName, TargetPlayerName),
    is_different_team(PlayerName, TargetPlayerName),
    player_class(PlayerName, sniper),
    player_hp(TargetPlayerName, HP),
    HP =< 150.

% Может ли Player повергнуть TargetPlayer ударом в спину (доступно только шпиону)?
can_backstab_player(PlayerName, TargetPlayerName) :-
    is_different_player(PlayerName, TargetPlayerName),
    is_different_team(PlayerName, TargetPlayerName),
    player_class(PlayerName, spy),
    player(TargetPlayerName),
    \+ has_item(TargetPlayerName, razorback).

% Может ли игрок полностью восстановить свое здоровье с помощью аптечки типа HealthPackType?
can_fully_heal_with_health_pack(PlayerName, HealthPackType) :-
    player_class(PlayerName, Class),
    player_hp(PlayerName, HP),
    health_pack_factor(HealthPackType, Factor),
    class_max_hp(Class, MaxHP),
    HP + MaxHP * Factor >= MaxHP.

% Примеры
% Каких игроков могут повергнуть снайперы?
% can_headshot_player(PlayerName, TargetPlayerName).

% Каких игроков может повергнуть ударом в спину terry (шпион команды BLU)?
% can_backstab_player('terry', TargetPlayerName).


q(P1, P2) :- player_team(P1, Team1), player_team(P2, Team2), Team1 == Team2.
