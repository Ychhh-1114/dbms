package Users;

import java.io.Serializable;
import java.util.ArrayList;

import Configs.DBMSException;
import Configs.Loader;
import Elements.DataBase;
import Operations.AbstractOperation;
import Operations.CreateDataBase;
import Operations.CreateIndex;
import Operations.CreateTable;
import Operations.CreateView;
import Operations.DeleteDataBase;
import Operations.DeleteIndex;
import Operations.DeleteView;
import Operations.DropTable;
import Operations.Query;
import Operations.Show;
import Operations.Update;

public class User implements Serializable {

	private static final long serialVersionUID = 2L;

	public String name;
	public String passwd;
	public Privilage privilage;

	public User(String name, String passwd) {
		this.name = name;
		this.passwd = passwd;
	}

	///////////////////////////////////////////////////////////

	static private User user;

	static public User getUser(String uName, String passwd) throws DBMSException {
		if (user == null) {
			User loader = Loader.getUser(uName, passwd);
			if (loader.passwd == passwd)
				user = loader;
			else
				throw new DBMSException("passwd false!");
		}
		return user;
	}

	static public User getUser() throws DBMSException {
		if (user != null)
			return user;
		else
			throw new DBMSException("the user has not loginon");
	}

	//////////////////////////////////////////////////

	public void dropTable(String[] params) throws DBMSException {

		String tName = params[0];
		AbstractOperation operation = new DropTable();
		operation.operate(tName);

	}

	public void createTable(String[] params) throws DBMSException {
		String tName = params[0];
		AbstractOperation operation = new CreateTable();
		operation.operate(params);

	}

	public void deleteTable(String[] params) throws DBMSException {
		AbstractOperation operation = new DeleteDataBase();
		operation.operate(params);
	}

	public void createView(String[] params) throws DBMSException {
		AbstractOperation operation = new CreateView();
		operation.operate(params);

	}

	public void query(String[] params) throws DBMSException {
		AbstractOperation operation = new Query();
		operation.operate(params);

	}

	public void deleteView(String[] params) throws DBMSException {
		String vName = params[0];
		AbstractOperation operation = new DeleteView();
		operation.operate(vName);

	}

	public void createUser(String[] params) throws DBMSException {

		String uName = params[0];
		String passwd = params[1];
		if (this instanceof Root) {
			UserManage.createUser(uName.trim(), passwd.trim());
		} else
			throw new DBMSException("the current user has not privilege to create user");
	}

	public void deleteUser(String[] params) throws DBMSException {
		String uName = params[0];
		if (this instanceof Root) {
			UserManage.deleteUser(uName.trim());
		} else
			throw new DBMSException("the current user has not privilege to delete user");
	}

	public void changeDB(String[] params) throws DBMSException {
		String dName = params[0];
		if (Loader.isDBExists(dName.trim())) {
			DataBase.currentDataBase = dName.trim();
		} else
			throw new DBMSException("has not this database");

	}

	public void createDB(String[] params) throws DBMSException {

		if (this instanceof Root) {
			AbstractOperation operation = new CreateDataBase();
			operation.operate(params);
		} else
			throw new DBMSException("has not the privilege to create database!");

	}

	public void deleteDB(String[] params) throws DBMSException {
		if (this instanceof Root) {
			AbstractOperation operation = new DeleteDataBase();
			operation.operate(params);
		} else
			throw new DBMSException("has not the privilege to delete database!");
	}

	public void insert(String[] params) throws DBMSException {
		AbstractOperation operation = new DeleteDataBase();
		operation.operate(params);

	}

	public void updateTable(String[] params) throws DBMSException {

		AbstractOperation operation = new Update();
		operation.operate(params);

	}

	public void createIndex(String[] params) throws DBMSException {
		AbstractOperation operation = new CreateIndex();
		operation.operate(params);

	}

	public void deleteIndex(String[] params) throws DBMSException {
		AbstractOperation operation = new DeleteIndex();
		operation.operate(params);
	}

	public void grantUser(String[] params) {

	}

	public void revokeUser(String[] params) {

	}

	public void show(String[] params) throws DBMSException {
		String param = (String) params[0];
		AbstractOperation operation = new Show();
		operation.operate(param);
	}

	public void addPrivilege(String[] pList, String[] tNames) {
		if (this.privilage == null)
			this.privilage = new Privilage();
		for (String str : tNames) {
			for (String p : pList) {
				if (this.privilage.tMP.containsKey(str))
					if (this.privilage.tMP.get(str).contains(p))
						this.privilage.tMP.get(str).add(p);
			}
		}

	}

	public void revokePrivilege(String[] pList, String[] tNames) {
		if (this.privilage == null)
			this.privilage = new Privilage();
		for (String str : tNames) {
			for (String p : pList) {
				if (this.privilage.tMP.containsKey(str))
					if (this.privilage.tMP.get(str).contains(p))
						this.privilage.tMP.get(str).remove(p);
			}
		}
	}

}
