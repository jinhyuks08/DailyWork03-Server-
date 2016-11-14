package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;

public class BankTransferService implements BankService {

	@Override
	public void execute(Model model) {
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
		String sendMemberId = request.getParameter("sendMemberId");
		String receiveMemberId = request.getParameter("receiveMemberBalance");
		String transferBalance = request.getParameter("transferBalance");
		Connection con = null;
		boolean result = false;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false); //�듃�옖�옲�뀡 �떆�옉
			
			BankDAO dao = new BankDAO(con);
			boolean withrawResult = dao.updateWithraw(sendMemberId, transferBalance);
			boolean depositResult = dao.updateDeposit(receiveMemberId, transferBalance);
			boolean historyResult = dao.updateHistory(sendMemberId, receiveMemberId, transferBalance);
			if (withrawResult && depositResult && historyResult) {
				result = true;
				con.commit();
			} else {
				result = false;
				con.rollback();
			}
			model.addAttribute("RESULT", result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}