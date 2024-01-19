package DSGAuthPkg;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

public class UserEntry {

	String m_Userdn = "";
	DirContext m_ctx = null;

	public UserEntry(DirContext ctx, String userDN) {

		// connect to my domain controller
		m_ctx = ctx;
		m_Userdn = userDN;
	}

	public String GetValue(String attributeName) {
		Attributes atts = null;
		String retVal = "";
		try {
			atts = m_ctx.getAttributes(m_Userdn, new String[] { attributeName });
			retVal = atts.get(attributeName).get().toString();
		} catch (NamingException e) {
			return null;
		} catch (NullPointerException nullEx) {
			return null;
		}
		return retVal;
	}

}
