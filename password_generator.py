import secrets
import os
import subprocess
import pyperclip

class PasswordGenerator:

    UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
    DIGITS = "0123456789"
    SPECIAL_CHARACTERS = "!@#$%^&*()-_+=<>?"

    def __init__(self):
        self.password_gen_num = 5
        self.password_length = 12
        self.include_upper = True
        self.include_lower = True
        self.include_digits = True
        self.include_special_chars = True

    def set_gen_num(self, gen_num):
        self.password_gen_num = gen_num
    
    def set_pass_length(self, length):
        self.password_length = length

    def has_upper(self, include_upper):
        self.include_upper = include_upper

    def has_lower(self, include_lower):
        self.include_lower = include_lower

    def has_digits(self, include_digits):
        self.include_digits = include_digits

    def has_specials(self, include_special):
        self.include_special_chars = include_special

    def generate_password(self):
        includes = [self.include_upper, self.include_lower, self.include_digits, self.include_special_chars]
        all_chars = [self.UPPERCASE, self.LOWERCASE, self.DIGITS, self.SPECIAL_CHARACTERS]
        included_chars = ""
        random_nums = []
        build_password = ""

        for i in range(self.password_length):
            for j in range(len(includes)):
                if includes[j] == False:
                    all_chars[j] = ""
            
            for j in range(len(all_chars)):
                included_chars += all_chars[j]
            temp = secrets.randbelow(len(included_chars))

            random_nums.append(temp)

        for i in range(self.password_length):
            temp_char = included_chars[random_nums[i]]
            build_password += temp_char
        
        return build_password

    def password_list(self):
        passwords = []
        print("\n>>> Your Passwords <<<")
        for i in range(1, self.password_gen_num + 1):
            password = self.generate_password()
            print(" " + str(i) + ". " + password)
            passwords.append(password)
        
        return passwords
    
    def options(self):
        print("\n>>> Options")
        print(" 1. Generate Passwords")
        print(" 2. Edit Generation Options")
        print(" 3. Save Password(s) to File")
        print(" 4. Copy Password(s) to Clipboard")
        print(" 5. Exit")

    def pass_gen_options(self):
        print("\n >> Password Generation Options")
        print("  1. Change Generation Amount")
        print("  2. Change Password(s) Length")
        print("  3. Change Characters Included")
        print("  4. Back")

    def pass_char_options(self):
        print("\n  >> Password Character Inclusion/Exclusion")
        print("   1. Exclude Uppercase Letters" if self.include_upper == True else "   1. Include Uppercase Letters")
        print("   2. Exclude Lowercase Letters" if self.include_lower == True else "   2. Include Lowercase Letters")
        print("   3. Exclude Digits" if self.include_digits == True else "   3. Include Digits")
        print("   4. Exclude Special Characters" if self.include_special_chars == True else "   4. Include Special Characters")
        print("   5. Back")

        if self.include_upper == False and self.include_lower == False and self.include_digits == False and self.include_special_chars == False:
            print("\n   Error: Must include at least one type of character | Options Reset")
            self.has_upper(True)
            self.has_lower(True)
            self.has_digits(True)
            self.has_specials(True)
            self.pass_char_options()
    
    def save_to_file(self, passwords):
        path = os.path.join(os.path.expanduser("~"), "Desktop", "passwords.txt")
        file = None

        if os.path.isfile(path):
            print("File already exists, adding to file...")
            file = open(path, "a")

        else:
            print("New file created...")
            file = open(path, "x")
            file.write(">>> Your Passwords <<<\n")

        for i in range(len(passwords)):
            file.write(" " + passwords[i] + "\n")
        
        print("Passwords Saved to Path: " + path)
        file.close()

    def copy_to_clipboard(self, passwords):
        all_passwords = ""
        for i in range(len(passwords)):
            all_passwords += passwords[i] + "\n"
        pyperclip.copy(all_passwords)
        print("Password(s) Saved to Clipboard!")

if __name__=="__main__": 
    password_gen = PasswordGenerator()
    passwords = password_gen.password_list()

    while True:
        password_gen.options()
        try:
            answer = int(input("> Option #: "))
        except ValueError:
            print("Error: Enter an integer")
            continue

        match answer:
            case 1:
                passwords = password_gen.password_list()
            
            case 2:
                while True:
                    password_gen.pass_gen_options()
                    try:
                        answer2 = int(input(" > Option #: "))
                    except ValueError:
                        print("Error: Enter an integer")
                        continue

                    match answer2:
                        case 1:
                            while True:
                                try:
                                    answer3 = int(input(" # of Generations: "))
                                    break
                                except ValueError:
                                    print("Error: Enter an integer")

                            password_gen.set_gen_num(answer3)
                            print(" Generation Number Set!")

                        case 2:
                            while True:
                                try:
                                    answer3 = int(input(" Password Length #: "))
                                    break
                                except ValueError:
                                    print("Error: Enter an integer")
                            password_gen.set_pass_length(answer3)
                            print(" Password Length Set!")

                        case 3:
                            while True:
                                password_gen.pass_char_options()
                                try:
                                    answer3 = int(input("  > Option #: "))
                                except ValueError:
                                    print("Error: Enter an integer")
                                    continue

                                match answer3:
                                    case 1:
                                        password_gen.has_upper(False) if password_gen.include_upper == True else password_gen.has_upper(True)
                                        
                                    case 2:
                                        password_gen.has_lower(False) if password_gen.include_lower == True else password_gen.has_lower(True)
                                        
                                    case 3:
                                        password_gen.has_digits(False) if password_gen.include_digits == True else password_gen.has_digits(True)
                                        
                                    case 4:
                                        password_gen.has_specials(False) if password_gen.include_special_chars == True else password_gen.has_specials(True)

                                    case 5:
                                        break
                                    
                                    case _:
                                        print("Error: Method does not exist")
                        
                        case 4:
                            break

                        case _:
                            print("Error: Method does not exist")
            
            case 3:
                password_gen.save_to_file(passwords)

            case 4:
                password_gen.copy_to_clipboard(passwords)
                
            case 5:
                break

            case _:
                print("Error: Method does not exist")