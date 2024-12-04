import React from "react";
import { MdEmail } from "react-icons/md";
import Button from "../../../../components/Form/Button/index";

export default function ForgotPasswordForm() {
    return (
        <form>
            {/* Email Input */}
            <div className="mb-4">
                <div className="flex items-center border rounded-lg px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500">
                    {/* <FontAwesomeIcon icon={faEnvelope} className="text-gray-500 mr-3" /> */}
                    <MdEmail className="text-gray-500 mr-3" />
                    <input
                        type="text"
                        placeholder="Nhập email"
                        className="w-full focus:outline-none"
                    />
                </div>
            </div>

            {/* Submit Button */}
            <div className="flex justify-center">
                <Button className="forgot-password-button">Quên mật khẩu</Button>
            </div>
        </form>
    );
}
