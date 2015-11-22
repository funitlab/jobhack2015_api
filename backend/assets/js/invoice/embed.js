/* 
 * Code by Moonclerk.com!
 */

(function () {
    this.MoonclerkEmbed = function () {
        function t(t) {
            var o, e;
            for (o in t)
                e = t[o], this.options[o] = t[o];
            if (!window.postMessage)
                throw"This browser does not support postMessage.";
            this.addEvent(window, "message", this.bindMethod(this.resizeFrame, this))
        }
        var o, e, n, r, i;
        return t.prototype.options = {
            checkoutToken: null, host: "app.moonclerk.com", ssl: !0, width: "100%", height: 0, cid: null
        }, t.prototype.scrollOffsetTop = 0, e = "", o = 0, r = "", n = 0, i = null, t.prototype.display = function () {
            return document.getElementById("mc" + this.options.checkoutToken).innerHTML = this.buildFrame()
        }, t.prototype.buildFrame = function () {
            return'<iframe id="frame-mc' + this.options.checkoutToken + '"\n  height="' + this.options.height + '"\n  frameborder="0"\n  scrolling="no"\n  style="width:' + this.widthStyle() + ';border:none;"\n  src="' + this.frameUrl() + '"\n  allowtransparency="true">\n  <a href="' + this.linkUrl() + '" title="checkout">Complete Checkout</a>\n</iframe>'
        }, t.prototype.widthStyle = function () {
            return isNaN(this.options.width) ? this.options.width : "" + this.options.width + "px"
        }, t.prototype.frameUrl = function () {
            var t;
            return t = "" + this.linkUrl() + "?embed=true", null != this.options.cid ? "" + t + "&cid=" + this.options.cid : t
        }, t.prototype.linkUrl = function () {
            return"" + this.baseUrl() + "/pay/index/" + this.options.checkoutToken
        }, t.prototype.baseUrl = function () {
            return this.protocol() + this.options.host
        }, t.prototype.protocol = function () {
            return this.options.ssl ? "https://" : "http://"
        }, t.prototype.resizeFrame = function (t) {
            var i, s;
            if (t.origin === this.baseUrl() && (i = this.parseMessage(t.data), e = "frame-mc" + i.checkoutHash, o = i.height, e !== r || o !== n)) {
                if (s = document.getElementById(e.toString()), null === s)
                    return;
                return s.style.height = o.toString() + "px", this.scrollIntoView(s, i.scrollTop), r = e, n = o
            }
        }, t.prototype.scrollIntoView = function (t, o) {
            var e, n, r, i, s;
            return null == o && (o = !1), r = this.findTop(t) - this.scrollOffsetTop, e = r + t.offsetHeight, i = window.pageYOffset, n = e - 250, o || i > n ? (s = Math.abs(r - 20), window.scrollTo(window.pageXOffset, s)) : void 0
        }, t.prototype.addEvent = function (t, o, e) {
            return t.attachEvent ? (t["e" + o + e] = e, t[o + e] = function () {
                return t["e" + o + e](window.event)
            }, t.attachEvent("on" + o, t[o + e])) : t.addEventListener(o, e, !1)
        }, t.prototype.bindMethod = function (t, o) {
            return function () {
                return t.apply(o, arguments)
            }
        }, t.prototype.findTop = function (t) {
            var o;
            for (o = 0; t.offsetParent; )
                o += t.offsetTop, t = t.offsetParent;
            return o
        }, t.prototype.parseMessage = function (t) {
            var o, e, n, r, i, s;
            for (o = {}, s = t.split("&"), r = 0, i = s.length; i > r; r++)
                e = s[r], n = e.split("="), 2 === n.length && (o[n[0]] = function () {
                    switch (n[0]) {
                        case"scrollTop":
                            return"true" === n[1];
                        case"height":
                            return parseInt(n[1]);
                        default:
                            return n[1]
                        }
                }());
            return o
        }, t
    }()
}).call(this);