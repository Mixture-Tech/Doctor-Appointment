import React from "react";
import InputField from "./InputField";
import { faEnvelope, faLock } from "@fortawesome/free-solid-svg-icons";

export default function LoginForm() {
    return (
        <form>
            {/* Email Input */}
            <InputField
                label="Email"
                type="text"
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

            {/* Forgot Password */}
            <div className="flex justify-end mb-6">
                <a href="#" className="text-sm text-gray-600 hover:underline">
                    Quên mật khẩu?
                </a>
            </div>

            {/* Submit Button */}
            <button
                type="submit"
                className="w-full py-2 text-white font-bold bg-primary-500 rounded-full hover:opacity-90"
            >
                Đăng nhập
            </button>
        </form>
    );
}
