package com.example.demo.Entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RensouForm {

	private Integer user_id;
	private Integer sheet_id;
	private String sheet_name;
	private List<NewWordList> nWordList;
	private List<ExistingWordList> eWordList;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = Integer.parseInt(user_id);
	}

	public Integer getSheet_id() {
		return sheet_id;
	}

	public void setSheet_id(Integer sheet_id) {
		this.sheet_id = sheet_id;
	}

	public void setSheet_id(String sheet_id) {
		this.sheet_id = Integer.parseInt(sheet_id);
	}

	public String getSheet_name() {
		return sheet_name;
	}

	public void setSheet_name(String sheet_name) {
		this.sheet_name = sheet_name;
	}

	public List<NewWordList> getnWordList() {
		return nWordList;
	}

	public void setnWordList(List<NewWordList> nWordList) {
		this.nWordList = nWordList;
	}

	public List<ExistingWordList> geteWordList() {
		return eWordList;
	}

	public void seteWordList(List<ExistingWordList> eWordList) {
		this.eWordList = eWordList;
	}

	public static class NewWordList {

		private String word;

		public void setWord(String word) {
			this.word = word;
		}

		public String getWord() {
			return word;
		}

		@Override
		public String toString() {
			return word;
		}
	}

	public static class ExistingWordList {

		private String word;

		public void setWord(String word) {
			this.word = word;
		}

		public String getWord() {
			return word;
		}

		@Override
		public String toString() {
			return word;
		}
	}
}
