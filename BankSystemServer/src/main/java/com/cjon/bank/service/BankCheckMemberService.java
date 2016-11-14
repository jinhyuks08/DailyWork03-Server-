package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankCheckMemberService implements BankService {

	@Override
	public void execute(Model model) {
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		String checkMemberId = (String) model.asMap().get("checkMemberId");
		Connection con = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);

			BankDAO dao = new BankDAO(con);
			ArrayList<BankDTO> list = dao.checkMemberId(checkMemberId);
			if (list != null) {
				con.commit();
			} else {
				con.rollback();
			}
			model.addAttribute("RESULT", list);
			System.out.println("..list : " + model.toString());

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
