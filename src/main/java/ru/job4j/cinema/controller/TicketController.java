package ru.job4j.cinema.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;

    public TicketController(TicketService ticketService, FilmSessionService filmSessionService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/buy/{sessionId}")
    public String createBuyForm(@PathVariable int sessionId, Model model) {
        Optional<FilmSessionDto> filmSessionDtoOptional = filmSessionService.findById(sessionId);
        if (filmSessionDtoOptional.isEmpty()) {
            model.addAttribute("message", "Сеанс с таким идентификатором не найден!");
            return "errors/404";
        }
        model.addAttribute("filmSession", filmSessionDtoOptional.get());
        model.addAttribute("rows", 10);
        model.addAttribute("places", 10);
        return "tickets/buyForm";
    }

    @PostMapping("/buy")
    public String buyTicket(@RequestParam int sessionId,
                            @RequestParam int rowNumber,
                            @RequestParam int placeNumber,
                            Model model,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";
        }
        Ticket ticket = new Ticket(0, sessionId, rowNumber, placeNumber, user.getId());
        var savedTicketOptional = ticketService.buy(ticket);
        if (savedTicketOptional.isEmpty()) {
            model.addAttribute("message", "Не удалось приобрести билет на заданное место. "
                    + "Вероятно оно уже занято. Перейдите на страницу бронирования билетов и попробуйте снова.");
            return "errors/404";
        }
        redirectAttributes.addFlashAttribute("message", "Вы успешно приобрели билет. "
                + "Ряд: " + rowNumber + ", место: " + placeNumber + ".");
        return "redirect:/tickets/success";
    }

    @GetMapping("/success")
    public String success() {
        return "tickets/success";
    }
}
