<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="util.InputValidator,util.AreaCheckEntry,java.util.List" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="ru-RU">

<head>
    <meta charset="UTF-8">
    <title>ЛР2</title>
    <link href="https://fonts.cdnfonts.com/css/luminari" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="styles/style.css" />
</head>
<body>
    <header>
        <div id="info">
            <div id="info-lab-name">
                <h1>Лабораторная работа №2 (веб)</h1>
            </div>
            <div class="horizontal-container">
                <div id="info-name" class="form-element">
                    <span>Султанов Артур Радикович</span>
                </div>
                <div id="info-group" class="form-element">
                    <span>P3213</span>
                </div>
                <div id="info-variant" class="form-element">
                    <span>Вариант №2317</span>
                </div>
            </div>
        </div>
    </header>

    <div id="content">
        <div id="form-container" class="vertical-container">
            <form method="POST" onsubmit="return validateForm();" action="controller">
                <div id="form" class="vertical-container">
                    <!--
                        Каскадность: комбинирование стилей из разных источников.
                        Здесь - .form-element, .border
                    -->
                    <div class="form-element border">
                        <canvas id="areacheck-canvas" height="500" width="500"></canvas>
                    </div>

                    <div class="horizontal-container">
                        <div id="form-x" class="form-element border">
                            <label for="x">Выберите координату X:</label>
                            <select name="x" id="x" required>
                            <% for (int i = InputValidator.MIN_X; i <= InputValidator.MAX_X; i++) { %>
                                <option value="<%= i %>"><%= i %></option>
                            <% } %>
                            </select>
                        </div>

                        <div id="form-y" class="form-element border">
                            <label for="y">Введите координату Y:</label>
                            <input
                                type="text"
                                name="y"
                                id="y"
                                required
                                placeholder="[${ InputValidator.MIN_Y }; ${ InputValidator.MAX_Y }]"
                                data-min-y="${ InputValidator.MIN_Y }"
                                data-max-y="${ InputValidator.MAX_Y }"
                            />
                        </div>

                        <div id="form-r" class="form-element border">
                            <div class="vertical-container">
                                <label for="r">Выберите значение R:</label>
                                <div class="horizontal-container">
                                    <% for (int i = InputValidator.MIN_R; i <= InputValidator.MAX_R; i++) { %>
                                        <input type="radio" name="r" value="<%= i %>" <%= i == InputValidator.MIN_R ? "checked" : "" %>><%= i %>
                                    <% } %>
                                </div>
                                <span class="error" id="canvas-r-error"></span>
                            </div>
                        </div>

                        <input type="text" name="responseType" value="page" style="display: none;" />
                    </div>

                    <div id="form-button" class="form-element">
                        <input type="submit" value="Проверить">
                    </div>
                </div>
            </form>

            <table role="grid">
                <thead>
                    <tr>
                        <th>X</th>
                        <th>Y</th>
                        <th>R</th>
                        <th>Внутри</th>
                        <th>Время</th>
                    </tr>
                    <tbody id="result-table">
                        <% for (AreaCheckEntry entry : (List<AreaCheckEntry>) request.getAttribute("results")) { %>
                            <tr>
                                <td><%= entry.x() %></td>
                                <td><%= entry.y() %></td>
                                <td><%= entry.r() %></td>
                                <td><%= entry.isInside() %></td>
                                <td class="timestamp"><%= entry.timestamp().getEpochSecond() %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </thead>
            </table>
        </div>
    </div>

    <script src="scripts/script.js"></script>
    <script src="scripts/timestamp.js"></script>
    <script src="scripts/canvas.js"></script>
</body>
</html>
