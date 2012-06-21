desc "Compile"
task :build do
  system("javac ./src/*.java -d ./classes")
end
