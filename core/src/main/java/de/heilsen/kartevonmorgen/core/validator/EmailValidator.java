package de.heilsen.kartevonmorgen.core.validator;

import java.util.regex.Pattern;

//TODO: implement own rfc 5322 compliant validator
public class EmailValidator {

    private static final String WSP = "[\\t ]";
    private static final String CRLF = "(\\r\\n)";
    private static final String FWS = String.format("((%s*%s)?%s+)", WSP, CRLF, WSP);  //FIXME: obs-fws part missing
    private static final String CFWS = String.format("(%s)", FWS); //FIXME: comment part missing

    private static final String ALPHA = "[A-Za-z]";
    private static final String DIGIT = "[0-9]";
    private static final String SPECIAL = "[!#$%&'*+-/=?^_`{|}~]";
    private static final String ATEXT = String.format("(%s|%s|%s)", ALPHA, DIGIT, SPECIAL);
    private static final String ATOM = String.format("(%s?%s+%s?)", CFWS, ATEXT, CFWS);
    private static final String DOT_ATOM_TEXT = String.format("(%s+(\\.%s+)*)", ATEXT, ATEXT);
    private static final String DOT_ATOM = String.format("(%s?%s%s?)", CFWS, DOT_ATOM_TEXT, CFWS);
//    private static final String ADDR_SPEC = LOCAL_PART + "@" + DOMAIN_PART;
//    private static final String LOCAL_PART = DOT_ATOM


    private static final String EMAIL_PART = "[^\\x00-\\x1F()<>@,;:\\\\\".\\[\\]\\s]";
    private static final String DOMAIN_PATTERN = EMAIL_PART + "+(\\." + EMAIL_PART
                                                 + "+)*";
    private static final String IPv4_PATTERN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
            .compile("^" + EMAIL_PART + "+(\\." + EMAIL_PART + "+)*@(" + DOMAIN_PATTERN
                     + "|" + IPv4_PATTERN + ")$", Pattern.CASE_INSENSITIVE);

}
