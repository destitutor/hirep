package net.hexabrain.hireo.web.bookmark.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.hexabrain.hireo.web.bookmark.dto.BookmarkedCompanyResponse;
import net.hexabrain.hireo.web.bookmark.service.BookmarkService;
import net.hexabrain.hireo.web.common.security.CurrentUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("/accounts/bookmark")
    public String showBookmarks(
        @CurrentUser User user,
        Model model
    ) {
        Page<BookmarkedCompanyResponse> companyBookmarks = bookmarkService.getCompanyBookmarks(user,
            Pageable.ofSize(5));
        model.addAttribute("companyBookmarks", companyBookmarks);
        return "/account/bookmark";
    }
}
