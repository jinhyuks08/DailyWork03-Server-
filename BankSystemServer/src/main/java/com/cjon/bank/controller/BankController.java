package com.cjon.bank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cjon.bank.dto.BankDTO;
import com.cjon.bank.service.BankCheckMemberService;
import com.cjon.bank.service.BankDepositService;
import com.cjon.bank.service.BankSelectAllMemberService;
import com.cjon.bank.service.BankSelectMemberService;
import com.cjon.bank.service.BankService;
import com.cjon.bank.service.BankTransferService;
import com.cjon.bank.service.BankWithrawService;

@Controller
public class BankController {
	private BankService service;
	private DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		System.out.println("0");
		this.dataSource = dataSource;
	}

	@RequestMapping(value = "/selectAllMember")
	public void selectAllMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		service = new BankSelectAllMemberService();
		model.addAttribute("dataSource", dataSource);
		service.execute(model);
		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");

		String json = null;
		ObjectMapper om = new ObjectMapper();
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@RequestMapping(value = "/selectMember")
	public void selectMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		service = new BankSelectMemberService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("memberId", request.getParameter("memberId"));
		System.out.println("memberId" + request.getParameter("memberId"));
		service.execute(model);

		BankDTO dto = (BankDTO) model.asMap().get("RESULT");

		String json = null;
		ObjectMapper om = new ObjectMapper();
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(dto);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@RequestMapping(value = "/deposit")
	public void deposit(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		service = new BankDepositService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result = (Boolean) model.asMap().get("RESULT");
		
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/withraw")
	public void withraw(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");

		service = new BankWithrawService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);

		boolean result = (Boolean) model.asMap().get("RESULT"); 
		
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/transfer")
	public void transfer(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");
		model.addAttribute("request", request);

		service = new BankTransferService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("request", request);
		service.execute(model);
		
		boolean result = (Boolean) model.asMap().get("RESULT"); 

		response.setContentType("text/plain; charset=utf8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(callback + "(" + result + ")");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/checkMember")
	public void checkMember(HttpServletRequest request, HttpServletResponse response, Model model) {
		String callback = request.getParameter("callback");
		String checkMemberId = request.getParameter("checkMemberId");

		service = new BankCheckMemberService();
		model.addAttribute("dataSource", dataSource);
		model.addAttribute("checkMemberId", checkMemberId);
		service.execute(model);

		ArrayList<BankDTO> list = (ArrayList<BankDTO>) model.asMap().get("RESULT");

		String json = null;
		ObjectMapper om = new ObjectMapper();
		try {
			json = om.defaultPrettyPrintingWriter().writeValueAsString(list);
			response.setContentType("text/plain; charset=utf8");
			response.getWriter().println(callback + "(" + json + ")");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
