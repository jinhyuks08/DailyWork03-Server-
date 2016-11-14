package com.cjon.bank.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.ui.Model;

import com.cjon.bank.dao.BankDAO;
import com.cjon.bank.dto.BankDTO;

public class BankSelectAllMemberService implements BankService {

	@Override
	public void execute(Model model) {
		DataSource dataSource = (DataSource) model.asMap().get("dataSource");
		Connection con = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			BankDAO dao = new BankDAO(con);
			ArrayList<BankDTO> list = dao.selectAllMember();
			if (list != null) {
				con.commit();
			} else {
				con.rollback();
			}
			model.addAttribute("RESULT", list);

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