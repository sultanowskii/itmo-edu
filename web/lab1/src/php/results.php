<?php
session_start();

if (isset($_SESSION["results"]) && is_array($_SESSION["results"])) {
    echo "<table>";
    echo "
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат</th>
            <th>Время запроса</th>
            <th>Время выполнения (сек)</th>
        </tr>";

    foreach ($_SESSION["results"] as $result) {
        echo "<tr>";
        echo "<td>" . $result["x"] . "</td>";
        echo "<td>" . $result["y"] . "</td>";
        echo "<td>" . $result["r"] . "</td>";
        echo "<td>" . $result["result"] . "</td>";
        echo "<td class=\"timestamp\">" . $result["timestamp"] . "</td>";
        echo "<td>" . number_format($result["execution_time"], 5) . "</td>";
        echo "</tr>";
    }
    echo "</table>";
} else {
    echo "<p>Пока нет результатов.</p>";
}
?>
