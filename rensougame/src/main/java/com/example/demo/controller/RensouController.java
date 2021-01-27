package com.example.demo.controller;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.Entity.Node;
import com.example.demo.Entity.RensouForm;
import com.example.demo.Entity.Sheet;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserForm;
import com.example.demo.exception.CreateAccountException;
import com.example.demo.exception.EditAccountException;
import com.example.demo.exception.LoginException;
import com.example.demo.service.RensouService;

@Controller
@SessionAttributes(types = { User.class, JSONArray.class })
public class RensouController {

	@Autowired
	private RensouService rensouService;

	@RequestMapping("/top")
	public String top() {
		return "rensou_top";
	}

	@RequestMapping("/howto")
	public String howto() {
		return "rensou_howto";
	}

	@RequestMapping("/createAccount")
	public String createAccount(Model model) {
		model.addAttribute("user", new UserForm());
		return "rensou_newaccount";
	}

	@RequestMapping(value = "/doCreateAccount", method = RequestMethod.POST)
	public String doCreateAccount(@ModelAttribute(name = "user") UserForm form, Model model) {
		try {
			rensouService.doCreateAccount(form);
			User loginUser = rensouService.doLogin(form);
			model.addAttribute("loginUser", loginUser);
			return "rensou_create_success";
		} catch (CreateAccountException cae) {
			model.addAttribute("msg", cae.getMessage());
			return "rensou_newaccount";
		} catch (LoginException le) {
			model.addAttribute("msg", le.getMessage());
			return "rensou_newaccount";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return "rensou_newaccount";
		}
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserForm());
		return "rensou_login";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String dologin(@ModelAttribute(name = "user") UserForm form, Model model) {
		try {
			User loginUser = rensouService.doLogin(form);
			if (loginUser == null) {
				model.addAttribute("msg", "パスワードが違います");
				return "rensou_login";
			}
			model.addAttribute("loginUser", loginUser);
			return mypage(loginUser, model);
		} catch (LoginException le) {
			model.addAttribute("msg", le.getMessage());
			return "rensou_login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました。");
			return "rensou_login";
		}
	}

	@RequestMapping("/doLogout")
	public String doLogout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "rensou_top";
	}

	@RequestMapping("/mypage")
	public String mypage(@ModelAttribute("loginUser") User loginUser, Model model) {
		try {
			List<Sheet> sheetList = rensouService.getSheetList(loginUser);
			String json = "{\"sheet_list\":[";
			for (int i = 0; i < sheetList.size(); i++) {
				json = json + " { "
						+ " \"sheet_name\" : \"" + sheetList.get(i).getSheet_name()
						+ "\" , \"sheet_id\" : \"" + sheetList.get(i).getSheet_id() +
						"\" } ";
				if (i != sheetList.size() - 1) {
					json = json + ",";
				}
			}
			json = json + "]}";
			model.addAttribute("json", json);
			return "rensou_mypage";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return top();
		}
	}

	@RequestMapping("/editAccount")
	public String doEdit(Model model) {
		model.addAttribute("user", new UserForm());
		return "rensou_edit_account";
	}

	@RequestMapping("/doEditAccount")
	public String doEditAccount(@ModelAttribute(name = "user") UserForm form,
			@ModelAttribute(name = "loginUser") User loginUser, Model model) {
		try {
			rensouService.doEditAccount(form, loginUser);
			model.addAttribute("loginUser", rensouService.refreshLoginUser(loginUser));
			return mypage(loginUser, model);
		} catch (EditAccountException eae) {
			eae.printStackTrace();
			model.addAttribute("msg", eae.getMessage());
			return "rensou_edit_account";
		}
	}

	@RequestMapping("noSheetGame")
	public String game() {
		return "rensougame";
	}

	@RequestMapping("/openSheetGame")
	public String openSheet(@RequestParam("sheet_id") int sheet_id, @ModelAttribute("loginUser") User loginUser,
			@ModelAttribute("form") RensouForm rensouForm,
			Model model) {
		List<Node> eNodeList = rensouService.openSheet(sheet_id);
		String json = "{\"node_list\":[";
		for (int i = 0; i < eNodeList.size(); i++) {
			json = json + " { "
					+ " \"node_name\" : \"" + eNodeList.get(i).getNode_name()
					+ "\" , \"node_id\" : \"" + eNodeList.get(i).getNode_id() +
					"\" } ";
			if (i != eNodeList.size() - 1) {
				json = json + ",";
			}
		}
		json = json + "]}";
		model.addAttribute("json", json);

		rensouForm.setSheet_id(sheet_id);
		rensouForm.setUser_id(loginUser.getUser_id());
		model.addAttribute("rensou", rensouForm);
		return "rensougame";
	}

	@RequestMapping(value = "/saveSheet", method = RequestMethod.POST)
	public String saveSheet(@ModelAttribute("form") RensouForm rensouForm, Model model) {
		try {
			rensouService.saveSheet(rensouForm);
			model.addAttribute("msg", "シートを保存しました!");
			return "rensou_mypage";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "保存に失敗しました");
			model.addAttribute("rensou", rensouForm);
			return "rensougame";
		}
	}
}
