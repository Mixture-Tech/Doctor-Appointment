import React from "react";
import LoginForm from "./components/LoginForm";
import Logo from "../../../components/Form/Auth/Logo";

export default function Login() {
  return (
      <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-primary-50 to-primary-200">
        <div className="w-full max-w-md bg-white rounded-lg shadow-lg p-8">
          <Logo />
          <h2 className="text-3xl font-bold text-center mb-6">Đăng nhập</h2>
          <LoginForm />
          <div className="text-center my-4 flex justify-center text-gray-600 text-sm">
            <p className="">
              Chưa có tài khoản?
            </p>
            <a href="#" className="ms-1 hover:underline font-semibold">
              Đăng ký ngay 
            </a>
          </div>
        </div>
      </div>
  );
}
