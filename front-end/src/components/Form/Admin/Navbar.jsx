import React, { useState, useCallback } from "react";
import { CiMenuFries } from "react-icons/ci";
import { FiUser } from "react-icons/fi";
import { TbLogout2 } from "react-icons/tb";
import { IoSearchOutline } from "react-icons/io5";
import { debounce } from 'lodash';

export default function Navbar({ toggleSidebar, onSearch }) {
    const [accountMenuOpen, setAccountMenuOpen] = useState(false);
    const [searchTerm, setSearchTerm] = useState('');
    const [isSearching, setIsSearching] = useState(false);

    const debouncedSearch = useCallback(
        debounce((searchValue) => {
            if (searchValue.trim() === '') {
                onSearch({});
            } else {
                const isAppointmentCode = /^[A-Z0-9]+$/.test(searchValue);
                const searchParams = {
                    ...(isAppointmentCode ? { appointmentCode: searchValue } : { username: searchValue })
                };
                onSearch(searchParams);
            }
            setIsSearching(false);
        }, 500),
        [onSearch]
    );

    const handleSearchChange = (e) => {
        const value = e.target.value;
        setSearchTerm(value);
        setIsSearching(true);
        debouncedSearch(value);
    };

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
            
            <div className="relative w-1/3">
                <input
                    type="text"
                    value={searchTerm}
                    onChange={handleSearchChange}
                    placeholder="Tìm kiếm theo tên hoặc mã lịch hẹn..."
                    className="border rounded-lg pl-10 pr-4 py-2 w-full focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                />
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                    <IoSearchOutline className="h-5 w-5 text-gray-400" />
                </div>
                {isSearching && (
                    <div className="absolute inset-y-0 right-0 pr-3 flex items-center">
                        <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-primary-500"></div>
                    </div>
                )}
            </div>

            <div className="flex items-center gap-[10px] relative">
                <div className="relative">
                    <img
                        src="https://genshin-guide.com/wp-content/uploads/raiden-shogun.webp"
                        alt="avatar"
                        className="w-[35px] h-[35px] rounded-full object-cover cursor-pointer"
                    />
                    <div className="w-[10px] h-[10px] rounded-full bg-green-500 absolute bottom-[0px] right-0 border-2 border-white"></div>
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
                    <div className="mt-3 border-t border-gray-200 pt-[5px]">
                        <p className="flex items-center gap-[5px] rounded-md p-[8px] pr-[45px] py-[3px] text-[1rem] text-red-500 hover:bg-red-50 cursor-pointer">
                            <TbLogout2/>
                            Đăng xuất
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}