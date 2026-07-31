import {
  isPlatformBrowser
} from "./chunk-ECPNNCQ5.js";
import {
  NgModule,
  PLATFORM_ID,
  Service,
  inject,
  setClassMetadata,
  ɵɵdefineInjector,
  ɵɵdefineNgModule,
  ɵɵdefineService
} from "./chunk-VSEI443O.js";

// node_modules/@angular/cdk/fesm2022/_platform-chunk.mjs
var hasV8BreakIterator;
try {
  hasV8BreakIterator = typeof Intl !== "undefined" && Intl.v8BreakIterator;
} catch {
  hasV8BreakIterator = false;
}
var Platform = class _Platform {
  _platformId = inject(PLATFORM_ID);
  isBrowser = this._platformId ? isPlatformBrowser(this._platformId) : typeof document === "object" && !!document;
  EDGE = this.isBrowser && /(edge)/i.test(navigator.userAgent);
  TRIDENT = this.isBrowser && /(msie|trident)/i.test(navigator.userAgent);
  BLINK = this.isBrowser && !!(window.chrome || hasV8BreakIterator) && typeof CSS !== "undefined" && !this.EDGE && !this.TRIDENT;
  WEBKIT = this.isBrowser && /AppleWebKit/i.test(navigator.userAgent) && !this.BLINK && !this.EDGE && !this.TRIDENT;
  IOS = this.isBrowser && /iPad|iPhone|iPod/.test(navigator.userAgent) && !("MSStream" in window);
  FIREFOX = this.isBrowser && /(firefox|minefield)/i.test(navigator.userAgent);
  ANDROID = this.isBrowser && /android/i.test(navigator.userAgent) && !this.TRIDENT;
  SAFARI = this.isBrowser && /safari/i.test(navigator.userAgent) && this.WEBKIT;
  static ɵfac = function Platform_Factory(__ngFactoryType__) {
    return new (__ngFactoryType__ || _Platform)();
  };
  static ɵprov = ɵɵdefineService({
    token: _Platform,
    factory: _Platform.ɵfac
  });
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(Platform, [{
    type: Service
  }], null, null);
})();

// node_modules/@angular/cdk/fesm2022/_scrolling-chunk.mjs
var RtlScrollAxisType;
(function(RtlScrollAxisType2) {
  RtlScrollAxisType2[RtlScrollAxisType2["NORMAL"] = 0] = "NORMAL";
  RtlScrollAxisType2[RtlScrollAxisType2["NEGATED"] = 1] = "NEGATED";
  RtlScrollAxisType2[RtlScrollAxisType2["INVERTED"] = 2] = "INVERTED";
})(RtlScrollAxisType || (RtlScrollAxisType = {}));

// node_modules/@angular/cdk/fesm2022/_shadow-dom-chunk.mjs
var shadowDomIsSupported;
function _supportsShadowDom() {
  if (shadowDomIsSupported == null) {
    const head = typeof document !== "undefined" ? document.head : null;
    shadowDomIsSupported = !!(head && (head.createShadowRoot || head.attachShadow));
  }
  return shadowDomIsSupported;
}
function _getShadowRoot(element) {
  if (_supportsShadowDom()) {
    const rootNode = element.getRootNode ? element.getRootNode() : null;
    if (typeof ShadowRoot !== "undefined" && ShadowRoot && rootNode instanceof ShadowRoot) {
      return rootNode;
    }
  }
  return null;
}
function _getFocusedElementPierceShadowDom() {
  let activeElement = typeof document !== "undefined" && document ? document.activeElement : null;
  while (activeElement && activeElement.shadowRoot) {
    const newActiveElement = activeElement.shadowRoot.activeElement;
    if (newActiveElement === activeElement) {
      break;
    } else {
      activeElement = newActiveElement;
    }
  }
  return activeElement;
}
function _getEventTarget(event) {
  if (event.composedPath) {
    try {
      return event.composedPath()[0];
    } catch {
    }
  }
  return event.target;
}

// node_modules/@angular/cdk/fesm2022/_passive-listeners-chunk.mjs
var supportsPassiveEvents;
function supportsPassiveEventListeners() {
  if (supportsPassiveEvents == null && typeof window !== "undefined") {
    try {
      window.addEventListener("test", null, Object.defineProperty({}, "passive", {
        get: () => supportsPassiveEvents = true
      }));
    } finally {
      supportsPassiveEvents = supportsPassiveEvents || false;
    }
  }
  return supportsPassiveEvents;
}
function normalizePassiveListenerOptions(options) {
  return supportsPassiveEventListeners() ? options : !!options.capture;
}

// node_modules/@angular/cdk/fesm2022/platform.mjs
var PlatformModule = class _PlatformModule {
  static ɵfac = function PlatformModule_Factory(__ngFactoryType__) {
    return new (__ngFactoryType__ || _PlatformModule)();
  };
  static ɵmod = ɵɵdefineNgModule({
    type: _PlatformModule
  });
  static ɵinj = ɵɵdefineInjector({});
};
(() => {
  (typeof ngDevMode === "undefined" || ngDevMode) && setClassMetadata(PlatformModule, [{
    type: NgModule,
    args: [{}]
  }], null, null);
})();

export {
  Platform,
  _getShadowRoot,
  _getFocusedElementPierceShadowDom,
  _getEventTarget,
  normalizePassiveListenerOptions
};
//# sourceMappingURL=chunk-DHMA2LIL.js.map
