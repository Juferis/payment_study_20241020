package kr.co.order.global.controller;

import kr.co.order.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final OrderService orderService;

    // 장바구니 화면 렌더링
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("order", orderService);
        return "cart";
    }
}
