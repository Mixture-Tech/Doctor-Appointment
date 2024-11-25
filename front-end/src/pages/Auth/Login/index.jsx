import React from "react";
import Logo from "./components/Logo";
import LoginForm from "./components/LoginForm";

export default function Login() {
  return (
      <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-primary-50 to-primary-200">
        <div className="w-full max-w-md bg-white rounded-lg shadow-lg p-8">
          <Logo />
          <h2 className="text-2xl font-semibold text-center mb-6">Đăng nhập</h2>
          <LoginForm />
          <div className="text-center my-4">
            <p className="text-gray-600">
              Chưa có tài khoản?{" "}
            </p>
            <a href="#" className="text-gray-600 hover:underline font-semibold">
              ĐĂNG KÝ
            </a>
          </div>
        </div>
      </div>
  );
}
