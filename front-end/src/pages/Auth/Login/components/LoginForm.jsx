import React from "react";
import { MdEmail } from "react-icons/md";
import { RiLockPasswordFill } from "react-icons/ri";
import Button from "../../../../components/Form/Button/index";
import InputField from "../../../../components/Form/Auth/AuthTextField";

export default function LoginForm() {
    return (
        <form>
            {/* Email Input */}
            <InputField
                label="Email"
                type="text"
                placeholder="Nhập email"
                icon={MdEmail}
            />

            {/* Password Input */}
            <InputField
                label="Mật khẩu"
                type="password"
                placeholder="Nhập mật khẩu"
                icon={RiLockPasswordFill}
            />

            {/* Forgot Password */}
            <div className="flex justify-end mb-6">
                <a href="#" className="text-sm text-gray-600 hover:underline">
                    Quên mật khẩu?
                </a>
            </div>

            <div className="flex justify-center">
                <Button className="login-button">Đăng nhập</Button>
            </div>
        </form>
    );
}
