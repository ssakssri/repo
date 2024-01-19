package DSGAuthPkg;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class Auth {
    /******************************************
     * member valiable & constant
     ******************************************/

    String m_Domain = "";
    String m_GCPath = "";

    String m_AdminName = "";
    String m_AdminPwd = "";

    String m_JoinedUserID = "";
    boolean m_IsDomainJoined = false;

    /******************************************
     * constructor
     ******************************************/

    public Auth(String domainName, String adminName, String adminPwd) {
        m_Domain = domainName;
        // m_GCPath = m_Domain;
        m_GCPath = "LDAP://" + domainName + ":3268";
        m_AdminName = adminName;
        m_AdminPwd = adminPwd;

        System.out.println(m_GCPath);
        System.out.println(m_AdminName);
        System.out.println(m_AdminPwd);
    }

    /******************************************
     * public functions
     ******************************************/

    public String getJoinedUserID() {
        return m_JoinedUserID;
    }

    public boolean getIsDomainJoined() {
        return m_IsDomainJoined;
    }

    /**
     * 
     * @param userName
     *            : Windows Logon User ID
     * @param password
     *            : Windows Logon password
     * @return
     * @throws UserNotFoundException
     */
    public boolean validateUser(String userName, String password) throws UserNotFoundException {
        Hashtable validateEnv = null;
        Hashtable envGC = null;

        LdapContext ctxGC = null;
        SearchControls searchCtls = null;

        String sUserDN = "";
        String sUserUPN = "";

        String searchFilter = "";

        try {
            // userName
            if (userName.length() < 1)
                return false;
            if (password.length() < 1)
                return false;

            envGC = new Hashtable();
            envGC.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            envGC.put(Context.SECURITY_PRINCIPAL, m_AdminName);
            envGC.put(Context.SECURITY_CREDENTIALS, m_AdminPwd);
            envGC.put(Context.PROVIDER_URL, m_GCPath);
            ctxGC = new InitialLdapContext(envGC, null);

            searchCtls = new SearchControls();
            String returnedAtts[] = { "DistinguishedName", "UserPrincipalName" };
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            if (IsUPNTypeID(userName))
                searchFilter = "(userPrincipalName=" + userName + ")";
            else
                searchFilter = "(sAMAccountName=" + userName + ")";

            String searchBase = "";

            int totalResults = 0;

            NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                totalResults++;

                Attributes attrs = sr.getAttributes();

                if (attrs != null) {
                    sUserDN = (String) attrs.get("DistinguishedName").get();
                    sUserUPN = (String) attrs.get("UserPrincipalName").get();
                }
            }
            ctxGC.close();
            // *** Validate User
            validateEnv = new Hashtable();
            validateEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            validateEnv.put(Context.SECURITY_PRINCIPAL, sUserUPN);
            validateEnv.put(Context.SECURITY_CREDENTIALS, password);
            validateEnv.put(Context.PROVIDER_URL, m_GCPath);
            DirContext ctx = new InitialDirContext(validateEnv);
            ctx.getAttributes("");

            ctx.close();

            return true;
        } catch (AuthenticationException authEx) {
            return false;
        } catch (NamingException e) {
            return false;
        }
    }

    /**
     * @param userName
     * @param appKeyName
     * @return
     * @throws UserNotFoundException
     */
    public boolean IsInMemberOf(String userName, String appKeyName) throws UserNotFoundException {

        Hashtable env = null;

        String groupDN = null;
        boolean ret = false;
        String sUserDN = "";
        try {
            sUserDN = GetDistinguishedName(userName);

            env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

            // set security credentials, note using simple cleartext
            // authentication
            // env.put(Context.SECURITY_AUTHENTICATION,"simple");
            env.put(Context.SECURITY_PRINCIPAL, m_AdminName);
            env.put(Context.SECURITY_CREDENTIALS, m_AdminPwd);
            env.put(Context.PROVIDER_URL, "LDAP://" + ExtractDomain(sUserDN) + ":389");

            // DirContext ctx = new InitialDirContext(env);
            LdapContext ctx = new InitialLdapContext(env, null);

            ctx.addToEnvironment("java.naming.ldap.attributes.binary", "tokenGroups");
            SearchControls searchCtls = new SearchControls();

            searchCtls.setSearchScope(SearchControls.OBJECT_SCOPE);
            String searchFilter = "(objectClass=user)";

            String searchBase = sUserDN;

            String returnedAtts[] = { "tokenGroups" };
            searchCtls.setReturningAttributes(returnedAtts);
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);

            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();

                Attributes attrs = sr.getAttributes();
                if (attrs != null) {

                    try {
                        for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
                            Attribute attr = (Attribute) ae.next();
                            // System.out.println("Attribute: " + attr.getID());
                            for (NamingEnumeration tokenGroupAE = attrs.get("tokenGroups").getAll(); tokenGroupAE.hasMore();) {
                                byte[] SID = (byte[]) tokenGroupAE.next();

                                String strSID = Sid2String(SID);
                                System.out.println("strSID :" + strSID);
                                String searchFilter1 = "(objectSID=" + strSID + ")";

                                SearchControls searchCtls1 = new SearchControls();
                                searchCtls1.setSearchScope(SearchControls.SUBTREE_SCOPE);

                                NamingEnumeration answer1 = ctx.search(getDefaultNamingContext(sUserDN), searchFilter1, searchCtls1);
                                while (answer1.hasMoreElements()) {
                                    SearchResult sr1 = (SearchResult) answer1.next();

                                    // System.out.println("Group Name : >>>" +
                                    // sr1.getName());

                                    Attributes attrs1 = sr1.getAttributes();
                                    if (attrs1 != null) {
                                        try {
                                            String groupName = (String) attrs1.get("cn").get();
                                            if (groupName.compareToIgnoreCase(appKeyName) == 0) {
                                                ret = true;
                                            }
                                        } catch (NullPointerException ne) {
                                            // System.err.println("Problem listing attributes: "
                                            // + ne);
                                        }
                                    }

                                }
                            }
                        }
                        ctx.close();
                    } catch (NamingException e) {
                        // System.err.println("Problem listing tokengroups: " +
                        // e);
                    }
                }
            }
        } catch (NamingException e) {
            // System.err.println("Problem searching directory: " + e);
        }
        return ret;
    }

    public UserEntry GetUserEntry(String userName) {
        String userDN = "";

        Hashtable validateEnv = null;
        Hashtable envGC = null;

        LdapContext ctxGC = null;

        try {
            if (userName.length() < 1)
                return null;

            userDN = GetDistinguishedName(userName);

            envGC = new Hashtable();
            envGC.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            envGC.put(Context.SECURITY_PRINCIPAL, m_AdminName);
            envGC.put(Context.SECURITY_CREDENTIALS, m_AdminPwd);
            envGC.put(Context.PROVIDER_URL, "ldap://" + m_Domain + ":389");
            ctxGC = new InitialLdapContext(envGC, null);

        } catch (Exception e) {
            return null;
        }

        return new UserEntry(ctxGC, userDN);
    }

    /******************************************
     * private functions
     ******************************************/

    private boolean IsUPNTypeID(String userName) {
        if (userName.indexOf('@') > -1)
            return true;
        else
            return false;
    }

    private String GetUserIDByUPN(String UPN) {
        return UPN.substring(0, UPN.indexOf('@'));
    }

    private String ExtractDomain(String sDN) {
        String sRet = "";
        String[] astr = null;

        astr = sDN.split(",");

        for (int i = 0; i < astr.length; i++) {
            if (astr[i].substring(0, 3).equals("DC=")) {
                if (sRet.length() > 0)
                    sRet += ".";

                sRet += astr[i].substring(3);
            }
        }

        return sRet;
    }

    private String getDefaultNamingContext(String sDN) {
        String sRet = "";
        String[] astr = null;

        astr = sDN.split(",");

        for (int i = 0; i < astr.length; i++) {
            if (astr[i].substring(0, 3).equals("DC=")) {
                if (sRet.length() > 0)
                    sRet += ",";

                sRet += astr[i];
            }
        }

        return sRet;
    }

    // convert the SID into string format
    private String Sid2String(byte[] SID) {
        long version;
        long authority;
        long count;
        long rid;
        String strSID;
        strSID = "S";
        version = SID[0];
        strSID = strSID + "-" + Long.toString(version);
        authority = SID[4];
        for (int i = 0; i < 4; i++) {
            authority <<= 8;
            authority += SID[4 + i] & 0xFF;
        }
        strSID = strSID + "-" + Long.toString(authority);
        count = SID[2];
        count <<= 8;
        count += SID[1] & 0xFF;
        for (int j = 0; j < count; j++) {
            rid = SID[11 + (j * 4)] & 0xFF;
            for (int k = 1; k < 4; k++) {
                rid <<= 8;
                rid += SID[11 - k + (j * 4)] & 0xFF;
            }
            strSID = strSID + "-" + Long.toString(rid);
        }
        return strSID;
    }

    private String GetDistinguishedName(String userID) {
        Hashtable envGC = null;

        LdapContext ctxGC = null;
        SearchControls searchCtls = null;

        String sUserDN = "";
        String searchFilter = "";

        NamingEnumeration answer = null;

        try {
            // *** Get User UPN(UserPrincipalName)
            envGC = new Hashtable();
            envGC.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            envGC.put(Context.SECURITY_PRINCIPAL, m_AdminName);
            envGC.put(Context.SECURITY_CREDENTIALS, m_AdminPwd);
            envGC.put(Context.PROVIDER_URL, m_GCPath);
            ctxGC = new InitialLdapContext(envGC, null);

            searchCtls = new SearchControls();
            String returnedAtts[] = { "DistinguishedName" };
            searchCtls.setReturningAttributes(returnedAtts);
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            if (IsUPNTypeID(userID))
                searchFilter = "(userPrincipalName=" + userID + ")";
            else
                searchFilter = "(sAMAccountName=" + userID + ")";

            String searchBase = "";

            // initialize counter to total the results
            int totalResults = 0;

            answer = ctxGC.search(searchBase, searchFilter, searchCtls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                totalResults++;

                Attributes attrs = sr.getAttributes();

                if (attrs != null)
                    sUserDN = (String) attrs.get("DistinguishedName").get();
            }

            ctxGC.close();
        } catch (Exception ex) {
            System.err.println("error: " + ex);
            // throw ex;
        }

        return sUserDN;
    }

}// end class
