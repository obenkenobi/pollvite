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
    async function _csrfWrapper(csrfSupplier) {
        return _getCsrfToken().then(csrf => csrfSupplier(csrf))
    }
    async function _csrfFetch(csrfHeaderSupplier) {
        return _csrfWrapper(csrf => csrfHeaderSupplier({"X-XSRF-TOKEN": csrf}))
    }
    return {
        absoluteUrl: _absoluteUrl,
        getCsrfToken: _getCsrfToken,
        csrfWrapper: _csrfWrapper,
        csrfFetch: _csrfFetch
    }
}