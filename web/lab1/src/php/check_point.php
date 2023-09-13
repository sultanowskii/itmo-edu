<?php
session_start();

function isInsideArea($x, $y, $r) {
    $circle_radius = $r / 2;
    $quarter_circle = ($x >= 0 && $y <= 0) && ($x * $x + $y * $y <= $circle_radius * $circle_radius);
    $square = ($x >= 0 && $y >= 0) && ($x <= $r && $y <= $r);
    $triangle = ($x <= 0 && $y >= 0) && (-$x + $y * 2 <= $r) && ($x >= -$r);

    return $quarter_circle || $square || $triangle;
}

if (
    isset($_GET["x"]) &&
    isset($_GET["y"]) &&
    isset($_GET["r"]) &&
    is_numeric($_GET["x"]) &&
    is_numeric($_GET["y"]) &&
    is_numeric($_GET["r"])
) {
    $x = $_GET["x"];
    $y = $_GET["y"];
    $r = $_GET["r"];

    $result = isInsideArea($x, $y, $r);
    $result_text = $result ? "Попадание" : "Непопадение";

    $_SESSION["results"][] = [
        "x" => $x,
        "y" => $y,
        "r" => $r,
        "result" => $result_text,
        "timestamp" => time(),
        "execution_time" => microtime(true) - $_SERVER["REQUEST_TIME_FLOAT"],
    ];
} else {
    $result_text = "Ошибка: недопустимые значения координат или радиуса";
}

include "results.php";
?>
