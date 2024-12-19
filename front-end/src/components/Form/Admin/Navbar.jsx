import React, {useState} from "react";
import {CiMenuFries} from "react-icons/ci";
import {FiUser} from "react-icons/fi";
import {TbLogout2} from "react-icons/tb";

export default function Navbar({ toggleSidebar }) {
    const [accountMenuOpen, setAccountMenuOpen] = useState(false);
    return (
        <div className="w-full h-16 bg-white flex justify-between items-center px-6 shadow-md">
            <button onClick={toggleSidebar} className="mr-4">
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth="1.5"
                    stroke="currentColor"
                    className="w-6 h-6 text-gray-700"
                >
                    <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="M3.75 7.5h16.5M3.75 12h16.5m-16.5 4.5h16.5"
                    />
                </svg>
            </button>
            <input
                type="text"
                placeholder="Search"
                className="border rounded-lg p-2 w-1/3"
            />
            <div className="flex items-center gap-[10px] relative">
                <div className="relative">
                    <img
                        src="https://genshin-guide.com/wp-content/uploads/raiden-shogun.webp"
                        alt="avatar"
                        className="w-[35px] h-[35px] rounded-full object-cover cursor-pointer"
                    />
                    <div
                        className="w-[10px] h-[10px] rounded-full bg-green-500 absolute bottom-[0px] right-0 border-2 border-white"></div>
                </div>
                <CiMenuFries
                    onClick={() => setAccountMenuOpen(!accountMenuOpen)}
                    className="text-[1.8rem] text-[#424242] cursor-pointer"
                />
                <div
                    className={`${
                        accountMenuOpen ? "translate-y-0 opacity-100 z-[9999]" : "translate-y-[10px] opacity-0 z-[-1]"
                    } bg-white w-max rounded-md boxShadow absolute top-[45px] right-0 p-[10px] flex flex-col transition-all duration-300 gap-[5px]`}
                >
                    <p className="flex items-center gap-[5px] rounded-md p-[8px] pr-[45px] py-[3px] text-[1rem] text-gray-600 hover:bg-gray-50 cursor-pointer">
                        <FiUser/>
                        Thông tin cá nhân
                    </p>
                    {/*<p className="flex items-center gap-[5px] rounded-md p-[8px] pr-[45px] py-[3px] text-[1rem] text-gray-600 hover:bg-gray-50">
                                <IoSettingsOutline />
                                Settings
                            </p>*/}
                    <div className="mt-3 border-t border-gray-200 pt-[5px]">
                        <p
                            /*onClick={handleLogout}*/
                            className="flex items-center gap-[5px] rounded-md p-[8px] pr-[45px] py-[3px] text-[1rem] text-red-500 hover:bg-red-50 cursor-pointer"
                        >
                            <TbLogout2/>
                            Đăng xuất
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}