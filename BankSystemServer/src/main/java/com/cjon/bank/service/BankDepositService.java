package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankDepositService implements BankService {

	@Override
	public void execute(Model model) {
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
		String memberId = request.getParameter("memberId");
		String memberBalance = request.getParameter("memberBalance");
		Connection con = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);

			BankDAO dao = new BankDAO(con);
			boolean result = dao.updateDeposit(memberId, memberBalance);
			if (result) {
				result = dao.updateStatement(memberId, memberBalance, "deposit");
				con.commit();
			} else {
				con.rollback();
			}
			model.addAttribute("RESULT", result);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e2) {
			}
		}

	}

}
