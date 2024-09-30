:- discontiguous monitor/1.
:- discontiguous monitor_size/2.
:- discontiguous monitor_resolution/2.
:- discontiguous monitor_refresh_rate/2.

% Стандартные размеры мониторов.
size(21).
size(24).
size(27).

% Стандартные разрешения мониторов.
resolution('FullHD').
resolution('2K').
resolution('4K').

% Стандартные частоты обновления мониторов (в Гц).
refresh_rate(60).
refresh_rate(144).
refresh_rate(240).

% Далее представлены мониторы и их характеристики.
monitor('HP T3U85AA').
monitor_size('HP T3U85AA', 21).
monitor_resolution('HP T3U85AA', 'FullHD').
monitor_refresh_rate('HP T3U85AA', 60).

monitor('Acer EG220Q Pbipx').
monitor_size('Acer EG220Q Pbipx', 21).
monitor_resolution('Acer EG220Q Pbipx', 'FullHD').
monitor_refresh_rate('Acer EG220Q Pbipx', 144).

% no 21'' FullHD 240hz

% no 21'' 2K 60hz

% no 21'' 2K 144hz

% no 21'' 2K 240hz

monitor('Asus ProArt PQ22UC').
monitor_size('Asus ProArt PQ22UC', 21).
monitor_resolution('Asus ProArt PQ22UC', '4K').
monitor_refresh_rate('Asus ProArt PQ22UC', 60).

% no 21'' 4K 144hz

% no 21'' 4K 240hz

monitor('BenQ GL2460HM').
monitor_size('BenQ GL2460HM', 24).
monitor_resolution('BenQ GL2460HM', 'FullHD').
monitor_refresh_rate('BenQ GL2460HM', 60).

monitor('Asus VG248QE').
monitor_size('Asus VG248QE', 24).
monitor_resolution('Asus VG248QE', 'FullHD').
monitor_refresh_rate('Asus VG248QE', 144).

monitor('Asus TUF GAMING VG259QM').
monitor_size('Asus TUF GAMING VG259QM', 24).
monitor_resolution('Asus TUF GAMING VG259QM', 'FullHD').
monitor_refresh_rate('Asus TUF GAMING VG259QM', 240).

monitor('Acer G257HU smidpx').
monitor_size('Acer G257HU smidpx', 24).
monitor_resolution('Acer G257HU smidpx', '2K').
monitor_refresh_rate('Acer G257HU smidpx', 60).

% rare!
monitor('AOC AG241QX').
monitor_size('AOC AG241QX', 24).
monitor_resolution('AOC AG241QX', '2K').
monitor_refresh_rate('AOC AG241QX', 144).

% rare!
monitor('Titan Army P2510S').
monitor_size('Titan Army P2510S', 24).
monitor_resolution('Titan Army P2510S', '2K').
monitor_refresh_rate('Titan Army P2510S', 240).

monitor('LG 24UD58-B').
monitor_size('LG 24UD58-B', 24).
monitor_resolution('LG 24UD58-B', '4K').
monitor_refresh_rate('LG 24UD58-B', 60).

% no 24'' 4K 144hz

% no 24'' 4K 240hz

monitor('BenQ GW2760HS').
monitor_size('BenQ GW2760HS', 27).
monitor_resolution('BenQ GW2760HS', 'FullHD').
monitor_refresh_rate('BenQ GW2760HS', 60).

monitor('Asus VG278Q').
monitor_size('Asus VG278Q', 27).
monitor_resolution('Asus VG278Q', 'FullHD').
monitor_refresh_rate('Asus VG278Q', 144).

monitor('Samsung LC27RG50FQNXZA').
monitor_size('Samsung LC27RG50FQNXZA', 27).
monitor_resolution('Samsung LC27RG50FQNXZA', 'FullHD').
monitor_refresh_rate('Samsung LC27RG50FQNXZA', 240).

monitor('Dell U2713HM').
monitor_size('Dell U2713HM', 27).
monitor_resolution('Dell U2713HM', '2K').
monitor_refresh_rate('Dell U2713HM', 60).

monitor('Gigabyte G27Q').
monitor_size('Gigabyte G27Q', 27).
monitor_resolution('Gigabyte G27Q', '2K').
monitor_refresh_rate('Gigabyte G27Q', 144).

monitor('MSI G274QPX').
monitor_size('MSI G274QPX', 27).
monitor_resolution('MSI G274QPX', '2K').
monitor_refresh_rate('MSI G274QPX', 240).

monitor('LG 27UD68-W').
monitor_size('LG 27UD68-W', 27).
monitor_resolution('LG 27UD68-W', '4K').
monitor_refresh_rate('LG 27UD68-W', 60).

monitor('LG 27GN950-B').
monitor_size('LG 27GN950-B', 27).
monitor_resolution('LG 27GN950-B', '4K').
monitor_refresh_rate('LG 27GN950-B', 144).

% no 27'' 4K 240hz

% Оптимален ли монитор для работы?
monitor_for_work(Monitor) :-
    monitor(Monitor),
    monitor_refresh_rate(Monitor, 60).

% Оптимален ли монитор для рисования/дизайна?
monitor_for_art(Monitor) :-
    monitor(Monitor),
    (
        monitor_refresh_rate(Monitor, 60);
        monitor_refresh_rate(Monitor, 144)
    ).

% Оптимален ли монитор для игры за ПК?
monitor_for_pc_gaming(Monitor) :-
    monitor(Monitor),
    (
        (
            monitor_resolution(Monitor, 'FullHD'),
            monitor_size(Monitor, 24)
        );
        (
            monitor_resolution(Monitor, '2K'),
            monitor_size(Monitor, 27)
        )
    ),
    (
        monitor_refresh_rate(Monitor, 144);
        monitor_refresh_rate(Monitor, 240)
    ).

% Оптимален ли монитор для консольных игр?
monitor_for_console_gaming(Monitor) :-
    monitor(Monitor),
    monitor_size(Monitor, 27),
    (
        monitor_resolution(Monitor, '2K');
        monitor_resolution(Monitor, '4K')
    ),
    monitor_refresh_rate(Monitor, 60).
