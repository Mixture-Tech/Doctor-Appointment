import axiosClient from "./axiosClient";
import { setToken } from "../auth/authUtils";

export const register = (registerRequest) => {
    const urlRegister="/auth/register"
    return axiosClient.post(urlRegister, registerRequest);
};

export const authenticate = async (authenticateRequest) => {
    try {
        const urlAuthenticate = "/auth/authenticate"
        const response = await axiosClient.post(urlAuthenticate, authenticateRequest);

        const { access_token, name, role } = response.data;
        setToken(access_token, name, role);

        return response;
    } catch (error) {
        console.error("Authenticate: ", error);
        throw error;
    }
};

// export const verifyEmail = (verifyRequest) => {
//     const url = urlAuth + "confirm-email";
//     return axiosClient.post(url, verifyRequest);
// };

export const forgotPassword = (forgotPasswordRequest) => {
    const urlForgotPassword = "/auth/forgot-password";
    return axiosClient.post(urlForgotPassword, forgotPasswordRequest);
}

export const changePassword = (changePasswordRequest) => {
    const urlChangePassword = "/auth/change-password";
    return axiosClient.post(urlChangePassword, changePasswordRequest);
}