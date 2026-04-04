package homeless.monkey.com.oauth2.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String message = "Unknown error";

        Integer statusCode = null;

        if (statusObj != null) {
            statusCode = Integer.valueOf(statusObj.toString());
        }

        if (statusCode != null) {
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                message = "Access denied (403) - У вас нет прав для просмотра этой страницы";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                message = "Unauthorized (401) - Пожалуйста, войдите в систему";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                message = "Page not found (404)";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                message = "Internal server error (500)";
            } else {
                message = "Error " + statusCode;
            }
        }

        model.addAttribute("errorMessage", message);
        model.addAttribute("statusCode", statusCode);

        return "error";
    }
}
