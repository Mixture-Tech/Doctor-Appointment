
import React from "react";

// react icons
import {CgFacebook} from "react-icons/cg";
import {BsInstagram, BsLinkedin, BsTwitter} from "react-icons/bs";
import {SlArrowUp} from "react-icons/sl";

export default function Footer() {

    return (
        <footer className="bg-primary-50 boxShadow rounded-xl w-full p-3 lg:p-4 relative">

            <div
                className="w-full flex items-center justify-center pt-[30px] flex-col gap-[20px] pb-[30px]">
                <img src="https://i.ibb.co/ZHYQ04D/footer-logo.png" alt="logo"
                     className="w-[150px]"/>

                <p className="text-[0.9rem] text-center sm:text-start text-gray-600">High level
                    experience in web design and development knowledge, producing quality work.
                </p>

                <button className="py-3 px-6 rounded-full bg-primary text-white hover:bg-primary-600">Contact Us</button>

                <div className="flex gap-[15px] text-black mt-4">
                    <a className="text-[1.3rem] p-1.5 cursor-pointer rounded-full bg-white text-text boxShadow">
                        <CgFacebook/>
                    </a>
                    <a className="text-[1.2rem] p-1.5 cursor-pointer rounded-full bg-white text-text boxShadow">
                        <BsTwitter/>
                    </a>
                    <a className="text-[1.2rem] p-1.5 cursor-pointer rounded-full bg-white text-text boxShadow">
                        <BsInstagram/>
                    </a>
                    <a className="text-[1.2rem] p-1.5 cursor-pointer rounded-full bg-white text-text boxShadow">
                        <BsLinkedin/>
                    </a>
                </div>
            </div>

            <div className="border-t border-gray-200 pt-[20px] flex items-center w-full flex-wrap gap-[20px] justify-center">
                <p className="text-[0.8rem] sm:text-[0.9rem] text-gray-600">© 2024 Mixture Team. All Rights Reserved.</p>  
            </div>
        </footer>
    );
};                  