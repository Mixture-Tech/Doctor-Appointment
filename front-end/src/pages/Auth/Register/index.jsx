import React from "react";
import RegisterForm from "./components/RegisterForm";
import Logo from "../../../components/Form/Auth/Logo";

export default function Register() {
  return (
      <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-primary-50 to-primary-200">
        <div className="w-full max-w-md bg-white rounded-lg shadow-lg p-6">
          <Logo />
          <h2 className="text-3xl font-bold text-center mb-6">Đăng ký</h2>
          <RegisterForm />
          <div className="text-center my-4 flex justify-center text-gray-600 text-sm">
            <p className="">
              Đã có tài khoản?
            </p>
            <a href="#" className="ms-1 hover:underline font-semibold">
              Đăng nhập 
            </a>
          </div>
        </div>
      </div>
  );
}
