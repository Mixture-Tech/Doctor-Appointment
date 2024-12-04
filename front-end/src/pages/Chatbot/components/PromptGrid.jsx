import React from "react";

export function PromptGrid() {
    const prompts = [
        "Tôi cảm thấy đau đầu, chóng mặt và nôn",
        "Tôi bị đau bụng kéo dài, không thuyên giảm",
        "Tôi cảm thấy khó thở, tức ngực khi vận động",
        "Tôi bị sốt cao và mệt mỏi kéo dài hơn 3 ngày",
    ];

    return (
        <div className="grid grid-cols-2 gap-4 p-4">
            {prompts.map((prompt, index) => (
                <div
                    key={index}
                    className="flex items-center justify-between p-4 border rounded-lg hover:shadow-lg cursor-pointer"
                >
                    <span className="font-medium text-gray-800">{prompt}</span>
                    <span className="text-primary-500 font-bold text-lg">+</span>
                </div>
            ))}
        </div>
    );
}
