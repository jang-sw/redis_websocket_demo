package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Login;
import com.example.demo.entity.CharacterSet;
import com.example.demo.repository.LoginRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.CharacterSetService;

import lombok.extern.slf4j.Slf4j;

@RestController
public class MainController {

	@Autowired
	LoginService loginService;
	
	/**
	 * ����(����) ȭ�� �̵�
	 * @param ModelAndView
	 * @return ModelAndView, setViewName("main")
	 * */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(ModelAndView mv) {	
    	mv.setViewName("main");
		return mv;
	}
	
	/**
	 * �α��� ȭ��
	 * @param ModelAndView
	 * @return ModelAndView, setViewName("login")
	 * */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mv) {
		mv.setViewName("login");
		return mv;
	}
	
	/**
	 * �α��� ���μ���
	 * @param ModelAndView, Login, HttpServletRequest, HttpServletResponse
	 * @return ModelAndView, setViewName (�α��� ������ "redirect:/main", ���н� "redirect:/")
	 * */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginProcess(ModelAndView mv, Login login, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		HttpSession httpSession = httpServletRequest.getSession();
		Map<String, String> page = new HashMap<>();
		page.put("page", "redirect:/");	
		loginService.findLoginListByIdAndPw(login)
			.stream()
			.forEach((loginInfo) ->{
				httpSession.setAttribute("loginInfo", loginInfo);
				page.put("page", "redirect:/main");
			});

		mv.setViewName(page.get("page"));
		return mv;
	}

}
