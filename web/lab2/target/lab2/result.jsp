<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="util.AreaCheckEntry,java.util.List" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="ru-RU">

<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css" />
    <link href="https://fonts.cdnfonts.com/css/luminari" rel="stylesheet" />
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

    <div id="content" class="vertical-container">
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <span class="error"><%= errorMessage %></span>
        <% } %>

        <table role="grid">
            <thead>
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Внутри</th>
                    <th>Время</th>
                </tr>
                <tbody>
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

        <a href="controller">Обратно к форме</a>
    </div>

    <script src="scripts/timestamp.js"></script>
</body>