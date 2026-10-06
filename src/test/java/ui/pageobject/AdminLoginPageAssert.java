package ui.pageobject;

public class AdminLoginPageAssert extends PageAssert {
    private final AdminLoginPage page;
    public AdminLoginPageAssert(AdminLoginPage page) { this.page = page; }
    public AdminLoginPageAssert isLoaded() {
        isVisible(page.username, page.password, page.signInButton);
        return this;
    }
    public AdminLoginPageAssert usernameIs(String expected) {
        hasValue(page.username, expected);
        return this;
    }
    public AdminLoginPageAssert passwordIs(String expected) {
        hasValue(page.password, expected);
        return this;
    }
}
