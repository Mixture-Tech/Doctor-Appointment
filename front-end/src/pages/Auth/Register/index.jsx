import React from "react";
import Logo from "./components/Logo";
import RegisterForm from "./components/RegisterForm";

export default function Register() {
  return (
      <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-primary-50 to-primary-200">
        <div className="w-full max-w-md bg-white rounded-lg shadow-lg p-6">
          <Logo />
          <h2 className="text-2xl font-bold text-center mb-3">Đăng ký</h2>
          <RegisterForm />
          <div className="text-center my-4">
            <p className="text-gray-600">Đã có tài khoản?{" "}</p>
            <a href="/login" className="text-gray-600 hover:underline font-semibold">
              ĐĂNG NHẬP
            </a>
          </div>
        </div>
      </div>
  );
}
