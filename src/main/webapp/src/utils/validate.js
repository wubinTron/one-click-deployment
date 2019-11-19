/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
    return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
    const valid_map = ['admin', 'editor']
    return valid_map.indexOf(str.trim()) >= 0
}

export function isAllNumber(str) {
    const reg = /^(\-|\+)?\d+(0)?$/
    return reg.test(str)
}

/* number Positive integer */
export function isvalidateNum(str) {
    const reg = /^[0-9]*[1-9][0-9]*$/;
    return reg.test(str);
}

/* number 0 and Positive integer */
export function isvalidateIntegerNum(str) {
    const reg = /^\d+$/;
    return reg.test(str);
}

/* twoDecimal */
export function twoDecimal(str) {
    const reg = /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
    return reg.test(str);
}

/* test ip */
export function isCorrectIp(str) {
    const reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    return reg.test(str)
}

/* test url*/

export function isCorrectUrl(str) {
    const reg = /^((https|http):\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
    return reg.test(str)
}