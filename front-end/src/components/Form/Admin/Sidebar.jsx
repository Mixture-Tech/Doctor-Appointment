// src/components/Sidebar.js
import React from "react";
import { Home, TableChart, BarChart, AccountCircle } from "@mui/icons-material";
import { FaUserCircle } from "react-icons/fa";
import { FaUserDoctor, FaSquareVirus } from "react-icons/fa6"
import { MdLocalHospital, MdDashboard } from "react-icons/md";
import { RiCalendarScheduleFill } from "react-icons/ri";
import Logo from "../../../assets/images/logo.png";
import {useNavigate} from "react-router-dom";

export default function Sidebar({ isOpen }) {
    const navigate = useNavigate();

    const navigateToHome = () => {
        navigate("/admin");
    };

    const navigateToAppointments = () => {
        navigate("/quan-ly-lich-hen");
    };

    return (
        <div
            className={`bg-white shadow-lg h-screen fixed top-0 left-0 transition-all duration-300 ${
                isOpen ? "w-64" : "w-0"
            }`}
            style={{
                overflow: isOpen ? "auto" : "hidden",
            }}
        >
            {isOpen && (
                <div>
                    <div className="flex-1 flex justify-center">
                        <img src={Logo} alt="logo" className="w-[80px]"/>
                    </div>
                    <ul>
                        <li className="p-4 text-gray-700 hover:bg-gray-200 cursor-pointer flex items-center" onClick={navigateToHome}>
                            <MdDashboard className="mr-2 h-6 w-6"/> Trang chủ
                        </li>
                        {/*<li className="p-4 text-gray-700 hover:bg-gray-200 cursor-pointer flex items-center">
                            <FaUserCircle className="mr-2 h-6 w-6"/> Quản lý người dùng
                        </li>
                        <li className="p-4 text-gray-700 hover:bg-gray-200 cursor-pointer flex items-center">
                            <FaUserDoctor className="mr-2 h-6 w-6"/> Quản lý bác sĩ
                        </li>
                        <li className="p-4 text-gray-700 hover:bg-gray-200 cursor-pointer flex items-center">
                            <MdLocalHospital className="mr-2 h-6 w-6"/> Quản lý chuyên khoa
                        </li>
                        <li className="p-4 text-gray-700 hover:bg-gray-200 cursor-pointer flex items-center">
                            <FaSquareVirus className="mr-2 h-6 w-6"/> Quản lý bệnh
                        </li>*/}
                        <li className="p-4 text-gray-700 hover:bg-gray-200 cursor-pointer flex items-center" onClick={navigateToAppointments}>
                            <RiCalendarScheduleFill className="mr-2 h-6 w-6"/> Quản lý lịch hẹn
                        </li>
                    </ul>
                </div>
            )}
        </div>
    );
}
