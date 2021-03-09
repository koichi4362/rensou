package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
@SessionAttributes(types = User.class)
public class RensouController {

	@Autowired
	private RensouService rensouService;

	@RequestMapping({"/top" , "/rensou"})
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
	public String doCreateAccount(@Validated @ModelAttribute(name = "user") UserForm form, BindingResult bindingResult,
			Model model) {
		try {
			if (bindingResult.hasErrors()) {
				model.addAttribute("msg", "入力情報にエラーがあります");
				return "rensou_newaccount";
			}
			rensouService.doCreateAccount(form);
			User loginUser = rensouService.doLogin(form);
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("msg", "アカウントを作成しました！");
			return mypage(loginUser, model);
		} catch (CreateAccountException cae) {
			model.addAttribute("msg", cae.getMessage());
			return createAccount(model);
		} catch (LoginException le) {
			model.addAttribute("msg", le.getMessage());
			return createAccount(model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return createAccount(model);
		}
	}

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserForm());
		return "rensou_login";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public String dologin(@Validated @ModelAttribute(name = "user") UserForm form, BindingResult bindingResult,
			Model model) {
		try {
			User loginUser = rensouService.doLogin(form);
			if (loginUser == null) {
				model.addAttribute("msg", "パスワードが違います");
				return login(model);
			}
			if (loginUser.getUser_role() != 1) {
				System.out.println("管理者ユーザーがプレイヤーユーザーとしてログインしようとしました。");
				model.addAttribute("msg", "ログインに失敗しました。");
				return login(model);
			}
			model.addAttribute("loginUser", loginUser);
			return mypage(loginUser, model);
		} catch (LoginException le) {
			model.addAttribute("msg", le.getMessage());
			return login(model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return login(model);
		}
	}

	@RequestMapping("/doLogout")
	public String doLogout(SessionStatus sessionStatus, Model model) {
		sessionStatus.setComplete();
		model.addAttribute("msg", "ログアウトしました");
		return top();
	}

	@RequestMapping("/mypage")
	public String mypage(@ModelAttribute("loginUser") User loginUser, Model model) {
		if (loginUser == null) {
			model.addAttribute("msg", "ログイン情報がありません。もう一度ログインしてください");
			return login(model);
		}
		try {
			String MySheetsJData = rensouService.getMySheetsJData(loginUser);
			model.addAttribute("json", MySheetsJData);
			model.addAttribute("sheet", new SheetForm());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return top();
		}
		return "rensou_mypage";
	}

	@RequestMapping("/editAccount")
	public String editAccount(Model model) {
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
			return editAccount(model);
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
			rensouForm.setSheet_name(rensouService.getSheetBySheetId(sheet_id).getSheet_name());
			String json = rensouService.createJsonNodesData(sheet_id);
			model.addAttribute("json", json);
		}
		rensouForm.setSheet_id(sheet_id);
		rensouForm.setUser_id(loginUser.getUser_id());
		model.addAttribute("rensou", rensouForm);
		return "rensougame";
	}

	@RequestMapping("viewSheetNoLogin")
	public String viewSheet(@RequestParam("sheet_id") int sheet_id, Model model) {
		String json = rensouService.createJsonNodesData(sheet_id);
		model.addAttribute("json", json);
		model.addAttribute("sheet", rensouService.getSheetBySheetId(sheet_id));
		return "rensouview";
	}

	@RequestMapping("viewSheet")
	public String viewSheet(@ModelAttribute("loginUser") User loginUser, @RequestParam("sheet_id") int sheet_id,
			@RequestParam("user_id") int user_id,
			Model model) {
		String json = rensouService.createJsonNodesData(sheet_id);
		model.addAttribute("json", json);
		model.addAttribute("sheet", rensouService.getSheetBySheetId(sheet_id));
		model.addAttribute("good_flag", rensouService.checkGood(loginUser.getUser_id(), sheet_id));
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
			model.addAttribute("json", rensouService.getPublicSheetsJData());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return top();
		}
		return "rensou_public_sheets";
	}

	@RequestMapping("/kanri_top")
	public String kanri_top(@ModelAttribute("loginManager") User loginManager, Model model) {
		if (!rensouService.isManager(loginManager)) {
			return kanri_login(model);
		}
		return "kanrisha_top";
	}

	@RequestMapping("kanri")
	public String kanri() {
		return "kanrisha";
	}

	@RequestMapping("kanri_login")
	public String kanri_login(Model model) {
		model.addAttribute("user", new UserForm());
		return "kanrisha_loign";
	}

	@RequestMapping(value = "/kanri_doLogin", method = RequestMethod.POST)
	public String kanri_dologin(@Validated @ModelAttribute(name = "user") UserForm form, BindingResult bindingResult,
			Model model) {
		try {
			User loginManager = rensouService.doLogin(form);
			if (loginManager == null) {
				model.addAttribute("msg", "パスワードが違います");
				return kanri_login(model);
			}
			if (loginManager.getUser_role() != 9) {
				System.out.println("管理者ではないユーザーが管理者としてログインしようとしました。");
				model.addAttribute("msg", "ログインに失敗しました。");
				return login(model);
			}
			model.addAttribute("loginManager", loginManager);
			return kanri_top(loginManager, model);
		} catch (LoginException le) {
			model.addAttribute("msg", le.getMessage());
			return kanri_login(model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return kanri_login(model);
		}
	}

	@RequestMapping("/kanri_doLogout")
	public String kanri_doLogout(SessionStatus sessionStatus, Model model) {
		sessionStatus.setComplete();
		model.addAttribute("msg", "ログアウトしました");
		return kanri_login(model);
	}

	@RequestMapping("kanri_newAccount")
	public String kanri_newAccount(@ModelAttribute("loginManager") User loginManager,
			Model model) {
		if (!rensouService.isManager(loginManager)) {
			return kanri_login(model);
		}
		model.addAttribute("user", new UserForm());
		return "kanrisha_newAccount";
	}

	@RequestMapping("kanri_doCreateAccount") //@ModelAttribute("loginManager") User loginManager,//←引数に記述したらログイン情報が入力値で上書きされた
	public String kanri_doCreateAccount(@Validated @ModelAttribute(name = "user") UserForm form,
			BindingResult bindingResult, Model model) {
		try {
			if (bindingResult.hasErrors()) {
				model.addAttribute("msg", "入力情報にエラーがあります");
				return "kanrisha_newAccount";
			}
			rensouService.kanri_doCreateAccount(form);
			model.addAttribute("msg", "アカウントを作成しました！");
			return "kanrisha_top";// kanri_top(loginManager, model);
		} catch (CreateAccountException cae) {
			model.addAttribute("msg", cae.getMessage());
			return "kanrisha_top";// kanri_newAccount(loginManager, model);
		} catch (LoginException le) {
			model.addAttribute("msg", le.getMessage());
			return "kanrisha_top";// kanri_newAccount(loginManager, model);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return "kanrisha_top"; //kanri_newAccount(loginManager, model);
		}
	}

	@RequestMapping("stopOpenPublic")
	public String stopOpenPublic(@ModelAttribute("loginManager") User loginManager,
			@RequestParam("sheet_id") int sheet_id, Model model) {
		if (!rensouService.isManager(loginManager)) {
			return kanri_login(model);
		}
		rensouService.stopOpenPublic(sheet_id);
		model.addAttribute("msg", sheet_id + "を公開禁止にしました");
		return public_sheets(model);
	}

	@RequestMapping("allowOpenPublic")
	public String allowOpenPublic(@ModelAttribute("loginManager") User loginManager,
			@RequestParam("sheet_id") int sheet_id, Model model) {
		if (!rensouService.isManager(loginManager)) {
			return kanri_login(model);
		}
		rensouService.allowOpenPublic(sheet_id);
		model.addAttribute("msg", sheet_id + "を公開可能にしました");
		return stoped_sheets(model);
	}

	@RequestMapping("stoped_sheets")
	public String stoped_sheets(Model model) {
		try {
			model.addAttribute("json", rensouService.gerStoppedSheetsJData());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "エラーが発生しました");
			return top();
		}
		return "kanrisha_stopped_sheets";
	}

	@RequestMapping("addGood")
	public String addGood(@RequestParam("sheet_id") Integer sheet_id, @RequestParam("user_id") Integer user_id,
			Model model) {
		rensouService.addGood(user_id, sheet_id);
		model.addAttribute("msg", "いいねしました！");
		return public_sheets(model);
	}

	@RequestMapping("cancelGood")
	public String cancelGood(@RequestParam("sheet_id") Integer sheet_id, @RequestParam("user_id") Integer user_id,
			Model model) {
		rensouService.cancelGood(user_id, sheet_id);
		model.addAttribute("msg", "いいねを取り消しました");
		return public_sheets(model);
	}
}
