function initHelper(initConf = {baseUrl: "/"}) {
    function _absoluteUrl(url) {
        const trimmedUrl =  url.startsWith("/") ? url.slice(1) : url;
        return initConf.baseUrl + trimmedUrl;
    }
    function _getCsrfTokenCookie() {
        return  document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
    }
    async function _getCsrfToken() {
        const cookie = _getCsrfTokenCookie()
        if (!cookie) {
            return fetch(_absoluteUrl("/api/auth/csrf")).then(() => _getCsrfTokenCookie())
        }
        return cookie
    }
    return {
        absoluteUrl: _absoluteUrl,
        getCsrfToken: _getCsrfToken,
        csrfWrapper: async function (csrfSupplier) {
            return _getCsrfToken().then(csrf => csrfSupplier(csrf))
        }
    }
}