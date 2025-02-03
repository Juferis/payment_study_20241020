package kr.co.order.global.controller;

import kr.co.order.domain.product.entity.Product;
import kr.co.order.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final ProductService productService;

    // 상품 리스트 렌더링
    @GetMapping
    public String showStore(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "store"; // templates/store.html
    }

    // 장바구니에 담기
    @PostMapping("/{id}/add-to-cart")
    public String addToCart(@PathVariable Long id, @RequestParam int quantity) {
        productService.addToCart(id, quantity);
        return "redirect:/cart";
    }
}
