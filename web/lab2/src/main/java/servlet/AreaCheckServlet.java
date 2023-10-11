package servlet;

import util.AreaCheckEntry;
import util.AreaChecker;
import util.InputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {
    private static final String CHECK_ENTRY_LIST_SESSION_KEY = "checkEntryList";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        var checkEntries = getResultList(session);

        request.setAttribute("results", checkEntries);
        request.getRequestDispatcher("/main.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String rawX = request.getParameter("x");
        String rawY = request.getParameter("y");
        String rawR = request.getParameter("r");

        int x;
        double y;
        int r;

        try {
            x = Integer.parseInt(rawX.replace(',', '.'));
            if (!InputValidator.isXValid(x)) {
                throw new ValidationException();
            }
        } catch (NumberFormatException | NullPointerException | ValidationException e) {
            returnErrorPage(request, response, "Введите валидный X.");
            return;
        }

        try {
            y = Double.parseDouble(rawY.replace(',', '.'));
            if (!InputValidator.isYValid(y)) {
                throw new ValidationException();
            }
        } catch (NumberFormatException | NullPointerException | ValidationException e) {
            returnErrorPage(request, response, "Введите валидный Y.");
            return;
        }

        try {
            r = Integer.parseInt(rawR.replace(',', '.'));
            if (!InputValidator.isRValid(r)) {
                throw new ValidationException();
            }
        } catch (NumberFormatException | NullPointerException | ValidationException e) {
            returnErrorPage(request, response, "Введите валидный R.");
            return;
        }

        boolean isInside = AreaChecker.checkArea(x, y, r);

        var entry = new AreaCheckEntry(x, y, r, isInside);
        HttpSession session = request.getSession();

        var checkEntries = getResultList(session);
        checkEntries.add(entry);
        setResultList(session, checkEntries);

        var responseType = request.getParameter("responseType");

        if (responseType.equals("json")) {
            response.setContentType("application/json");
            response.getWriter().append(entry.toJSON());
        } else if (responseType.equals("page")) {
            request.setAttribute("results", checkEntries);
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        }
    }

    private void returnErrorPage(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    private List<AreaCheckEntry> getResultList(HttpSession session) {
        List<AreaCheckEntry> checkEntryList = (List<AreaCheckEntry>) session.getAttribute(CHECK_ENTRY_LIST_SESSION_KEY);

        if (checkEntryList == null) {
            checkEntryList = new ArrayList<>();
        }

        return checkEntryList;
    }

    private void setResultList(HttpSession session, List<AreaCheckEntry> checkEntryList) {
        session.setAttribute(CHECK_ENTRY_LIST_SESSION_KEY, checkEntryList);
    }
}
