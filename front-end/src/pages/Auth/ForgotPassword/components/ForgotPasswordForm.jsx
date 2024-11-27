import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope } from "@fortawesome/free-solid-svg-icons";

export default function ForgotPasswordForm() {
    return (
        <form>
            {/* Email Input */}
            <div className="mb-4">
                <div className="flex items-center border rounded-lg px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500">
                    <FontAwesomeIcon icon={faEnvelope} className="text-gray-500 mr-3" />
                    <input
                        type="text"
                        placeholder="Nhập email"
                        className="w-full focus:outline-none"
                    />
                </div>
            </div>

            {/* Submit Button */}
            <button
                type="submit"
                className="w-full py-2 text-white font-bold bg-primary-500 rounded-full hover:opacity-90"
            >
                Gửi liên kết
            </button>
        </form>
    );
}
