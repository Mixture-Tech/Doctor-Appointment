import React from 'react'

export default function AppointmentTextField({ icon: Icon, type = "text", placeholder }) {
  return (
    <div className="w-[100%] relative">
      <Icon className="absolute top-3.5 left-3 text-[1.5rem] text-secondary-300" />
      <input
        type={type}
        placeholder={placeholder}
        className="peer border-secondary-100 border rounded-md outline-none pl-12 pr-4 py-3 w-full focus:border-primary transition-colors duration-300"
      />
    </div>
  );
}

// Usage example

/*
// import InputField from "./InputField";
import { RiAccountCircleLine, RiLockPasswordLine } from "react-icons/ri";
import { MdOutlineMail } from "react-icons/md";
import AppointmentTextField from "../../../components/Form/TextField/AppointmentTextField";

const Inputs = () => {
  return (
    <div className="space-y-4">
      <div>
        <AppointmentTextField icon={RiAccountCircleLine} type="text" placeholder="Tên người dùng" />
      </div>

      <div>
        <AppointmentTextField icon={RiLockPasswordLine} type="password" placeholder="Mật khẩu" />
      </div>

      <div>
        <AppointmentTextField icon={MdOutlineMail} type="email" placeholder="Địa chỉ Email" />
      </div>
    </div>
  );
};

export default Inputs;

*/