import React from "react";
import { FaUserMd } from "react-icons/fa";
import { MdEmail, MdPhone } from "react-icons/md";
import { RiLockPasswordFill } from "react-icons/ri";
import Button from "../../../../components/Form/Button/index";
import InputField from "../../../../components/Form/Auth/AuthTextField";

export default function RegisterForm() {
    return (
        <form>
            {/* Name Input */}
            <InputField
                label="Họ và tên"
                type="text"
                placeholder="Nhập họ và tên"
                icon={FaUserMd}
            />

            {/* Phone Input */}
            <InputField
                label="Số điện thoại"
                type="phone"
                placeholder="Nhập số điện thoại"
                icon={MdPhone}
            />

            {/* Email Input */}
            <InputField
                label="Email"
                type="email"
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

            {/* Submit Button */}
            <div className="flex justify-center">
                <Button className="register-button">Đăng ký</Button>
            </div>
        </form>
    );
}
