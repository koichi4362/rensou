package com.example.demo.controller;

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

import com.example.demo.Entity.RensouForm;
import com.example.demo.Entity.SheetForm;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserForm;
import com.example.demo.exception.CreateAccountException;
import com.example.demo.exception.EditAccountException;
import com.example.demo.exception.LoginException;
import com.example.demo.exception.SwitchPublicFlagException;
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
			model.addAttribute("msg", "エラーが発生しました");
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
		if (loginUser == null) {
			model.addAttribute("msg", "ログイン情報がありません。もう一度ログインしてください");
			return login(model);
		}
		try {
			model.addAttribute("json", rensouService.getJsonMySheets(loginUser));
			model.addAttribute("sheet", new SheetForm());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return top();
		}
		return "rensou_mypage";
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
			model.addAttribute("msg", "アカウント情報を変更しました！");
			return mypage(loginUser, model);
		} catch (EditAccountException eae) {
			eae.printStackTrace();
			model.addAttribute("msg", eae.getMessage());
			return "rensou_edit_account";
		}
	}

	@RequestMapping("noSheetGame")
	public String game(Model model) {
		model.addAttribute("rensou", new RensouForm());
		return "rensougame";
	}

	@RequestMapping("/openSheetGame")
	public String openSheet(@RequestParam("sheet_id") Integer sheet_id, @ModelAttribute("loginUser") User loginUser,
			@ModelAttribute("form") RensouForm rensouForm,
			Model model) {
		if (sheet_id != 0) {
			rensouForm.setSheet_name(rensouService.getSheetNameFormSheetId(sheet_id));
			String json = rensouService.createJsonNodesData(sheet_id);
			model.addAttribute("json", json);
		}
		rensouForm.setSheet_id(sheet_id);
		rensouForm.setUser_id(loginUser.getUser_id());
		model.addAttribute("rensou", rensouForm);
		return "rensougame";
	}

	@RequestMapping("viewSheet")
	public String viewSheet(@RequestParam("sheet_id") int sheet_id, Model model) {
		String json = rensouService.createJsonNodesData(sheet_id);
		model.addAttribute("json", json);
		model.addAttribute("sheet_name", rensouService.getSheetNameFormSheetId(sheet_id));
		return "rensouview";
	}

	@RequestMapping(value = "/saveSheet", method = RequestMethod.POST)
	public String saveSheet(@ModelAttribute("form") RensouForm rensouForm, @ModelAttribute("loginUser") User loginUser,
			Model model) {
		try {
			rensouService.saveSheet(rensouForm);
			model.addAttribute("msg", "シートを保存しました!");
			return mypage(loginUser, model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "保存に失敗しました");
			model.addAttribute("rensou", rensouForm);
			return "rensougame";
		}
	}

	@RequestMapping("/updateSheetName")
	public String updateSheetName(@ModelAttribute("sheet") SheetForm sheetForm,
			@ModelAttribute("loginUser") User loginUser, Model model) {
		rensouService.updateSheetName(sheetForm);
		model.addAttribute("msg", "シート名を変更しました！");
		return mypage(loginUser, model);
	}

	@RequestMapping("/switchPublicFlag")
	public String switchPublicFlag(@ModelAttribute("sheet") SheetForm sheetForm,
			@ModelAttribute("loginUser") User loginUser, Model model) {
		try {
			sheetForm = rensouService.switchPublicFlag(sheetForm);
		} catch (SwitchPublicFlagException spfe) {
			model.addAttribute("msg", spfe.getMessage());
			return mypage(loginUser, model);
		} catch (Exception e) {
			model.addAttribute("msg", "シートの公開に失敗しました");
			e.printStackTrace();
			return mypage(loginUser, model);
		}
		String returnMessage = "";
		if (sheetForm.getPublic_flag() == 0) {
			returnMessage = sheetForm.getSheet_name() + " を非公開にしました";
		} else if (sheetForm.getPublic_flag() == 1) {
			returnMessage = sheetForm.getSheet_name() + " を公開しました！";
		}
		model.addAttribute("msg", returnMessage);
		return mypage(loginUser, model);
	}

	@RequestMapping("/public_sheets")
	public String public_sheets(Model model) {
		try {
			model.addAttribute("json", rensouService.getJsonPublicSheets());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return top();
		}
		return "rensou_public_sheets";
	}

}
