import React, { useState } from "react";
import { IoIosSearch } from "react-icons/io";
import { CiMenuFries } from "react-icons/ci";
import { FiUser } from "react-icons/fi";
import { IoSettingsOutline } from "react-icons/io5";
import { TbLogout2} from "react-icons/tb";

export default function Navbar() {
  const [accountMenuOpen, setAccountMenuOpen] = useState(false);
  const [mobileSidebarOpen, setMobileSidebarOpen] = useState(false);

  return (
    <nav className="flex bg-primary-100 items-center justify-between w-full relative px-4">
      {/* Logo */}
      <div className="flex-1 flex justify-start">
        <img src="https://i.ibb.co/0BZfPq6/darklogo.png" alt="logo" className="w-[80px]" />
      </div>

      {/* Menu */}
      <ul className="flex-1 flex items-center justify-center gap-[20px] text-[1rem] text-[#424242] lg:flex hidden">
        <li className="hover:border-b-[#3B9DF8] border-b-[2px] border-transparent transition-all duration-500 cursor-pointer hover:text-primary-900 capitalize">home</li>
        <li className="hover:border-b-[#3B9DF8] border-b-[2px] border-transparent transition-all duration-500 cursor-pointer hover:text-primary-900 capitalize">about us</li>
        <li className="hover:border-b-[#3B9DF8] border-b-[2px] border-transparent transition-all duration-500 cursor-pointer hover:text-primary-900 capitalize">services</li>
        <li className="hover:border-b-[#3B9DF8] border-b-[2px] border-transparent transition-all duration-500 cursor-pointer hover:text-primary-900 capitalize">doctors</li>
        <li className="hover:border-b-[#3B9DF8] border-b-[2px] border-transparent transition-all duration-500 cursor-pointer hover:text-primary-900 capitalize">blog</li>
        <li className="hover:border-b-[#3B9DF8] border-b-[2px] border-transparent transition-all duration-500 cursor-pointer hover:text-primary-900 capitalize">contact</li>
      </ul>

      {/* Search and Account */}
      <div className="flex-1 flex items-center justify-end gap-[10px]">
        <div className="relative lg:flex hidden">
          <input
            className="py-1.5 pr-4 border border-text pl-10 rounded-full outline-none focus:border-[#3B9DF8]"
            placeholder="Search..."
          />
          <IoIosSearch className="absolute top-[9px] left-3 text-[#424242] text-[1.3rem]" />
        </div>

        <div
          className="flex items-center gap-[15px] relative"
          onClick={() => setAccountMenuOpen(!accountMenuOpen)}
        >
          <div className="relative">
            <img
              src="https://img.freepik.com/free-photo/portrait-man-laughing_23-2148859448.jpg"
              alt="avatar"
              className="w-[35px] h-[35px] rounded-full object-cover cursor-pointer"
            />
            <div className="w-[10px] h-[10px] rounded-full bg-green-500 absolute bottom-[0px] right-0 border-2 border-white"></div>
          </div>
          <h1 className="text-[1rem] font-[400] text-secondary sm:block hidden">Văn Hoàng</h1>

          <div
            className={`${
              accountMenuOpen ? "translate-y-0 opacity-100 z-[1]" : "translate-y-[10px] opacity-0 z-[-1]"
            } bg-white w-max rounded-md boxShadow absolute top-[45px] right-0 p-[10px] flex flex-col transition-all duration-300 gap-[5px]`}
          >
            <p className="flex items-center gap-[5px] rounded-md p-[8px] pr-[45px] py-[3px] text-[1rem] text-gray-600 hover:bg-gray-50">
              <FiUser />
              View Profile
            </p>
            <p className="flex items-center gap-[5px] rounded-md p-[8px] pr-[45px] py-[3px] text-[1rem] text-gray-600 hover:bg-gray-50">
              <IoSettingsOutline />
              Settings
            </p>
            <div className="mt-3 border-t border-gray-200 pt-[5px]">
              <p className="flex items-center gap-[5px] rounded-md p-[8px] pr-[45px] py-[3px] text-[1rem] text-red-500 hover:bg-red-50">
                <TbLogout2 />
                Logout
              </p>
            </div>
          </div>

          <CiMenuFries
            onClick={() => setMobileSidebarOpen(!mobileSidebarOpen)}
            className="text-[1.8rem] text-[#424242] cursor-pointer lg:hidden flex"
          />
        </div>
      </div>
    </nav>
  );
}
