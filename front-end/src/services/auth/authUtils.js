export const setToken = (token, name , role) => {
    const decodedToken = jwtDecode(token);
    const expirationTime = decodedToken.exp * 1000;

    Cookies.set(StorageKeys.ACCESS_TOKEN, token, { expires: new Date(expirationTime) });
    localStorage.setItem(StorageKeys.USER_NAME, name);
    localStorage.setItem(StorageKeys.USER_ROLE, role);
};
