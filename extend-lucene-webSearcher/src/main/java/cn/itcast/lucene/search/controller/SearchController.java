package cn.itcast.lucene.search.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.lucene.search.domian.Article;
import cn.itcast.lucene.search.service.ArticleIndexService;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ArticleIndexService articleIndexService;

    @RequestMapping("/index")
    public ModelAndView index(String keyword) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/search/index");
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView search(String keyword) {
        ModelAndView modelAndView = new ModelAndView();
        List<Article> resultList = articleIndexService.query("title", keyword, 10);
        modelAndView.addObject("resultList", resultList);
        modelAndView.setViewName("/search/result");
        return modelAndView;
    }

    @RequestMapping("/init")
    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/search/index");
        articleIndexService.createIndexFromDB();
        return modelAndView;
    }
}
