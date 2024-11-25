import React from "react";
import InputField from "./InputField";
import { faEnvelope, faLock, faUser, faPhone } from "@fortawesome/free-solid-svg-icons";

export default function RegisterForm() {
    return (
        <form>
            {/* Name Input */}
            <InputField
                label="Họ và tên"
                type="text"
                placeholder="Nhập họ và tên"
                icon={faUser}
            />

            {/* Phone Input */}
            <InputField
                label="Số điện thoại"
                type="phone"
                placeholder="Nhập số điện thoại"
                icon={faPhone}
            />

            {/* Email Input */}
            <InputField
                label="Email"
                type="email"
                placeholder="Nhập email"
                icon={faEnvelope}
            />

            {/* Password Input */}
            <InputField
                label="Mật khẩu"
                type="password"
                placeholder="Nhập mật khẩu"
                icon={faLock}
            />

            {/* Submit Button */}
            <button
                type="submit"
                className="w-full py-2 text-white font-bold bg-gradient-to-r from-primary-100 to-primary-500 rounded-full hover:opacity-90 my-3"
            >
                ĐĂNG KÝ
            </button>
        </form>
    );
}
