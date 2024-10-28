# person.py
class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def to_dict(self):
        return {"name": self.name, "age": self.age}

def create_person(name, age):
    return Person(name, age).to_dict()

def describe_person(person_dict):
    return f"{person_dict['name']} is {person_dict['age']} years old."
